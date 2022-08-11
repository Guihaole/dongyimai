package com.offcn.seckill.task;

import com.offcn.seckill.dao.SeckillGoodsMapper;
import com.offcn.seckill.entity.SeckillStatus;
import com.offcn.seckill.pojo.SeckillGoods;
import com.offcn.seckill.pojo.SeckillOrder;
import com.offcn.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MultiThreadingCreateOrder {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;
    /***
     * 多线程下单操作
     * 先响应，后续慢慢执行
     */
    @Async
    public void createOrder(){
        //队列获取排队线程
        SeckillStatus seckillStatus =(SeckillStatus) redisTemplate.boundListOps("SeckillOrderQueue").rightPop();
        //从队列中获取一个商品
        Object sgood = redisTemplate.boundListOps("SeckillGoodsCountList_" + seckillStatus.getGoodsId()).rightPop();
        if (sgood == null) {
            //清理当前用户的排队信息
            clearQueue(seckillStatus);
            return;
        }
        if (seckillStatus != null) {
            Long id = seckillStatus.getGoodsId();
            String username = seckillStatus.getUsername();
            String time = seckillStatus.getTime();
            //下单功能
            //1.查询库存
            SeckillGoods redisSeckillGoods = (SeckillGoods) redisTemplate.boundHashOps("SeckillGoods_" + time).get(id);
            //2.库存足够,生成订单;库存不足，抛异常
            if (redisSeckillGoods == null || redisSeckillGoods.getStockCount() <= 0) {
                throw new RuntimeException("已售罄!");
            }
            SeckillOrder seckillOrder = new SeckillOrder();
            seckillOrder.setId(idWorker.nextId());
            seckillOrder.setMoney(redisSeckillGoods.getCostPrice());
            seckillOrder.setCreateTime(new Date());
            seckillOrder.setSeckillId(id);
            seckillOrder.setUserId(username);
            seckillOrder.setStatus("0");
            redisTemplate.boundHashOps("SeckillOrder").put(username, seckillOrder);
            //3.扣减库存
            //redisSeckillGoods.setStockCount(redisSeckillGoods.getStockCount() - 1);
            //if (redisSeckillGoods.getStockCount() <= 0) {

            //商品库存-1
            Long surplusCount = redisTemplate.boundHashOps("SeckillGoodsCount").increment(id, -1);//商品数量递减
            redisSeckillGoods.setStockCount(surplusCount.intValue());    //根据计数器统计

            //判断当前商品是否还有库存
            if (surplusCount <= 0) {
                //不能减库存了
                seckillGoodsMapper.updateById(redisSeckillGoods);
                redisTemplate.boundHashOps("SeckillGoods_" + time).delete(id);
                //库存并未扣减成功，返回抢单成功
            } else {
                redisTemplate.boundHashOps("SeckillGoods_" + time).put(id, redisSeckillGoods);
            }
            //修改抢单状态，从排队状态改为下单成功，但未支付,并设值订单id 金额
            seckillStatus.setStatus(2);
            seckillStatus.setOrderId(seckillOrder.getId());
            seckillStatus.setMoney(Float.parseFloat(seckillOrder.getMoney()));
            redisTemplate.boundHashOps("UserQueueStatus").put(username,seckillStatus);
        }
    }
    /***
     * 清理用户排队信息
     *
     * @param seckillStatus
     */
    public void clearQueue(SeckillStatus seckillStatus) {
        //清理排队标示
        redisTemplate.boundHashOps("UserQueueCount").delete(seckillStatus.getUsername());

        //清理抢单标示
        redisTemplate.boundHashOps("UserQueueStatus").delete(seckillStatus.getUsername());
    }
}
