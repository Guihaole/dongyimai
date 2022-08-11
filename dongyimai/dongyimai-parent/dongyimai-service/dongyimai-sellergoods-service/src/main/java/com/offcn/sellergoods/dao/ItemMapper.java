package com.offcn.sellergoods.dao;
import com.offcn.order.pojo.OrderItem;
import com.offcn.sellergoods.pojo.Item;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Update;

/****
 * @Author:ujiuye
 * @Description:Item的Dao
 * @Date 2021/2/1 14:19
 *****/
public interface ItemMapper extends BaseMapper<Item> {
    /**
     * 递减库存
     * @param orderItem 结算了全部的购物车信息
     * @return
     */
    @Update("UPDATE tb_item SET num=num-#{num} WHERE id=#{itemId} AND num>=#{num}")
    int decrCount(OrderItem orderItem);
}
