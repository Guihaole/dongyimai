package com.offcn.sellergoods.service.impl;
import com.offcn.sellergoods.dao.SeckillOrderMapper;
import com.offcn.sellergoods.pojo.SeckillOrder;
import com.offcn.sellergoods.service.SeckillOrderService;
import com.github.pagehelper.PageHelper;
import com.offcn.entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
/****
 * @Author:ujiuye
 * @Description:SeckillOrder业务层接口实现类
 * @Date 2021/2/1 14:19
 *****/
@Service
public class SeckillOrderServiceImpl extends ServiceImpl<SeckillOrderMapper,SeckillOrder> implements SeckillOrderService {


    /**
     * SeckillOrder条件+分页查询
     * @param seckillOrder 查询条件
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageResult<SeckillOrder> findPage(SeckillOrder seckillOrder, int page, int size){
         Page<SeckillOrder> mypage = new Page<>(page, size);
        QueryWrapper<SeckillOrder> queryWrapper = this.createQueryWrapper(seckillOrder);
        IPage<SeckillOrder> iPage = this.page(mypage, queryWrapper);
        return new PageResult<SeckillOrder>(iPage.getTotal(),iPage.getRecords());
    }

    /**
     * SeckillOrder分页查询
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageResult<SeckillOrder> findPage(int page, int size){
        Page<SeckillOrder> mypage = new Page<>(page, size);
        IPage<SeckillOrder> iPage = this.page(mypage, new QueryWrapper<SeckillOrder>());

        return new PageResult<SeckillOrder>(iPage.getTotal(),iPage.getRecords());
    }

    /**
     * SeckillOrder条件查询
     * @param seckillOrder
     * @return
     */
    @Override
    public List<SeckillOrder> findList(SeckillOrder seckillOrder){
        //构建查询条件
        QueryWrapper<SeckillOrder> queryWrapper = this.createQueryWrapper(seckillOrder);
        //根据构建的条件查询数据
        return this.list(queryWrapper);
    }


    /**
     * SeckillOrder构建查询对象
     * @param seckillOrder
     * @return
     */
    public QueryWrapper<SeckillOrder> createQueryWrapper(SeckillOrder seckillOrder){
        QueryWrapper<SeckillOrder> queryWrapper = new QueryWrapper<>();
        if(seckillOrder!=null){
            // 主键
            if(!StringUtils.isEmpty(seckillOrder.getId())){
                 queryWrapper.eq("id",seckillOrder.getId());
            }
            // 秒杀商品ID
            if(!StringUtils.isEmpty(seckillOrder.getSeckillId())){
                 queryWrapper.eq("seckill_id",seckillOrder.getSeckillId());
            }
            // 支付金额
            if(!StringUtils.isEmpty(seckillOrder.getMoney())){
                 queryWrapper.eq("money",seckillOrder.getMoney());
            }
            // 用户
            if(!StringUtils.isEmpty(seckillOrder.getUserId())){
                 queryWrapper.eq("user_id",seckillOrder.getUserId());
            }
            // 商家
            if(!StringUtils.isEmpty(seckillOrder.getSellerId())){
                 queryWrapper.eq("seller_id",seckillOrder.getSellerId());
            }
            // 创建时间
            if(!StringUtils.isEmpty(seckillOrder.getCreateTime())){
                 queryWrapper.eq("create_time",seckillOrder.getCreateTime());
            }
            // 支付时间
            if(!StringUtils.isEmpty(seckillOrder.getPayTime())){
                 queryWrapper.eq("pay_time",seckillOrder.getPayTime());
            }
            // 状态
            if(!StringUtils.isEmpty(seckillOrder.getStatus())){
                 queryWrapper.eq("status",seckillOrder.getStatus());
            }
            // 收货人地址
            if(!StringUtils.isEmpty(seckillOrder.getReceiverAddress())){
                 queryWrapper.eq("receiver_address",seckillOrder.getReceiverAddress());
            }
            // 收货人电话
            if(!StringUtils.isEmpty(seckillOrder.getReceiverMobile())){
                 queryWrapper.eq("receiver_mobile",seckillOrder.getReceiverMobile());
            }
            // 收货人
            if(!StringUtils.isEmpty(seckillOrder.getReceiver())){
                 queryWrapper.eq("receiver",seckillOrder.getReceiver());
            }
            // 交易流水
            if(!StringUtils.isEmpty(seckillOrder.getTransactionId())){
                 queryWrapper.eq("transaction_id",seckillOrder.getTransactionId());
            }
        }
        return queryWrapper;
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(Long id){
        this.removeById(id);
    }

    /**
     * 修改SeckillOrder
     * @param seckillOrder
     */
    @Override
    public void update(SeckillOrder seckillOrder){
        this.updateById(seckillOrder);
    }

    /**
     * 增加SeckillOrder
     * @param seckillOrder
     */
    @Override
    public void add(SeckillOrder seckillOrder){
        this.save(seckillOrder);
    }

    /**
     * 根据ID查询SeckillOrder
     * @param id
     * @return
     */
    @Override
    public SeckillOrder findById(Long id){
        return  this.getById(id);
    }

    /**
     * 查询SeckillOrder全部数据
     * @return
     */
    @Override
    public List<SeckillOrder> findAll() {
        return this.list(new QueryWrapper<SeckillOrder>());
    }
}
