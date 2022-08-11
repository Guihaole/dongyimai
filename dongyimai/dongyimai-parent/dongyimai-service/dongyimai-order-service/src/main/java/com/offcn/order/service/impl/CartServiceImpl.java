package com.offcn.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.offcn.entity.Result;
import com.offcn.order.pojo.Cart;
import com.offcn.order.pojo.OrderItem;
import com.offcn.order.service.CartService;
import com.offcn.sellergoods.fegin.ItemFeign;
import com.offcn.sellergoods.pojo.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private ItemFeign itemFeign;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 添加购物车  itemid ,num
     * 2.1 如果有判断有没有对应得cart
     * 2.2  如果没有创建对应得cart item
     * 2.1.1 判断有没有对应得商品 数量增加
     * 2.1.2 判断没有创建item
     *
     * @param cartList 从redis中查询到得cartList
     * @param itemId
     * @param num
     * @return
     */

    @Override
    public List<Cart> addGoodsToCartList(List<Cart> cartList, Long itemId, Integer num) {
        //查询商家id
        Result<Item> itemResult = itemFeign.findById(itemId);
        Item item = itemResult.getData();
        if (item == null) {
            throw new RuntimeException("商品不存在");
        }
        if (!item.getStatus().equals("1")) {
            throw new RuntimeException("商品未上架");
        }
        String sellerId = item.getSellerId();
        //寻找有没有对应得cart
        Cart cart = searchCartBySellerId(cartList, sellerId);
        if (cart == null) {
            //没有创建对应得cart itemList item
            Cart cartNew = new Cart();
            cartNew.setSellerId(sellerId);
            cartNew.setSellerName(item.getSeller());
            List<OrderItem> orderItemList = new ArrayList<>();
            //创建orderItem订单项
            OrderItem orderItem = createOrderItem(item, num);
            orderItemList.add(orderItem);
            cartNew.setOrderItemList(orderItemList);
            cartList.add(cartNew);
        } else {
            //有cart 无需创建itemList
            //判断itemList中是否有item商品
            OrderItem orderItem = searchOrderItemByItemId(cart.getOrderItemList(), itemId);
            if (orderItem == null) {
                //没有对应得orderItem
                OrderItem orderItemNew = createOrderItem(item, num);
                cart.getOrderItemList().add(orderItemNew);
            } else {
                //有对应得orderItem,增加数量
                orderItem.setNum(orderItem.getNum() + num);
                orderItem.setTotalFee(String.valueOf(Double.valueOf(orderItem.getPrice()) * orderItem.getNum()));
                //如果数量操作后小于等于则移除
                if (orderItem.getNum()<=0) {
                    cart.getOrderItemList().remove(orderItem);
                }
                //如果移除cart得明细数量为0，则cart移除
                if (cart.getOrderItemList().size()==0) {
                    cartList.remove(cart);
                }
           }
        }
        return cartList;
    }

    /**
     * 判断itemList中是否有item商品
     *
     * @param orderItemList
     * @param itemId
     * @return
     */
    private OrderItem searchOrderItemByItemId(List<OrderItem> orderItemList, Long itemId) {
        for (OrderItem orderItem : orderItemList) {
            if (orderItem.getItemId().longValue() == itemId.longValue()) {
                return orderItem;
            }
        }
        return null;
    }

    /**
     * 根据订单明细创建订单项
     *
     * @param item
     * @param num
     * @return
     */
    private OrderItem createOrderItem(Item item, Integer num) {
        if (num <= 0) {
            throw new RuntimeException("数量非法");
        }
        OrderItem orderItem = new OrderItem();
        orderItem.setGoodsId(item.getGoodsId());
        orderItem.setItemId(item.getId());
        orderItem.setNum(num);
        orderItem.setPicPath(item.getImage());
        orderItem.setPrice(item.getPrice());
        orderItem.setSellerId(item.getSellerId());
        orderItem.setTitle(item.getTitle());
        orderItem.setTotalFee(String.valueOf(num * Double.parseDouble(item.getPrice())));
        return orderItem;
    }

    /**
     * 寻找有没有对应得cart
     *
     * @param cartList
     * @param sellerId
     * @return
     */
    private Cart searchCartBySellerId(List<Cart> cartList, String sellerId) {
        if (cartList.size() == 0) {
            return null;
        }
        for (Cart cart : cartList) {
            if (cart.getSellerId().equals(sellerId)) {
                return cart;
            }
        }
        return null;
    }

    /**
     * 从redis中查询购物车
     *
     * @param username
     * @return
     */
    @Override
    public List<Cart> findCartListFromRedis(String username) {
        System.out.println("查询购物车redis");
        List<Cart> cartList = (List<Cart>) redisTemplate.boundHashOps("cartList").get(username);
        if (cartList == null) {
            cartList = new ArrayList<>();
        }
        return cartList;
    }

    /**
     * 保存购物车到redis
     *
     * @param username
     * @return
     */
    @Override
    public void saveCartListToRedis(String username, List<Cart> cartList) {
        System.out.println("向redis存入购物车数据....." + username);
        //String s = JSON.toJSONString(cartList);
        redisTemplate.boundHashOps("cartList").put(username, cartList);
    }
}
