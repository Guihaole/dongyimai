package com.offcn.seckill.timer;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.offcn.seckill.dao.SeckillGoodsMapper;
import com.offcn.seckill.pojo.SeckillGoods;
import com.offcn.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Set;

//开启定时任务
@Component
public class SeckillGoodsPushTask {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;

    //1. 查询mysql数据同步到redis数据库
    @Scheduled(cron = "0/10 * * * * ?")
    public void loadGoodsPushRedis() {
        //1.1 获取集合数据段
        List<Date> dateMenus = DateUtil.getDateMenus();
        //16:00-18:00
        for (Date startTime : dateMenus) {
            System.out.println(DateUtil.date2Str(startTime));
            String extName = DateUtil.date2Str(startTime);
            //1.2构造符合条件得商品
            QueryWrapper<SeckillGoods> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("status", "1")
                    .gt("stock_count", 0)
                    .ge("start_time", DateUtil.date2StrFull(startTime))
                    .lt("end_time", DateUtil.date2StrFull(DateUtil.addDateHour(startTime, 2)));
            //1.3判断redis是否已经缓存过此数据
            //若没缓存则添加缓存,并设置2小时后自动删除,因为整个秒杀时段就2小时
            //若缓存过就不应再覆盖缓存,否则会盖掉库存等信息,导致秒杀扣减的库存又被初始化了回来
            Set keys = redisTemplate.boundHashOps("SeckillGoods_" + extName).keys();
            if (keys != null && keys.size() > 0) {
                queryWrapper.notIn("id",keys);
            }
            List<SeckillGoods> seckillGoodsList = seckillGoodsMapper.selectList(queryWrapper);
            System.out.println(seckillGoodsList);
            for (SeckillGoods seckillGoods : seckillGoodsList) {
                redisTemplate.boundHashOps("SeckillGoods_" + extName).put(seckillGoods.getId(),seckillGoods);
                redisTemplate.expireAt("SeckillGoods_" + extName,DateUtil.addDateHour(startTime,2));
                //商品数据队列存储，防止高并发超卖
                Long[] ids = pushIds(seckillGoods.getStockCount(), seckillGoods.getId());
                redisTemplate.boundListOps("SeckillGoodsCountList_"+seckillGoods.getId()).leftPushAll(ids);
                //自增计数器--防止库存余数混乱
                redisTemplate.boundHashOps("SeckillGoodsCount").increment(seckillGoods.getId(),
                        seckillGoods.getStockCount());
            }
        }
    }
    /***
     * 将商品ID存入到数组中
     *
     * @param len:长度
     * @param id     :值
     * @return
     */
    public Long[] pushIds(int len, Long id) {
        Long[] ids = new Long[len];
        for (int i = 0; i < ids.length; i++) {
            ids[i] = id;
        }
        return ids;
    }
}
