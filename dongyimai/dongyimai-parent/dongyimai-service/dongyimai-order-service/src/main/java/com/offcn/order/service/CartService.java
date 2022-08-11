package com.offcn.order.service;

import com.offcn.order.pojo.Cart;

import java.util.List;

public interface CartService {
    /**
     * 添加商品到购物车
     * @param cartList
     * @param itemId
     * @param num
     * @return
     */
    public List<Cart> addGoodsToCartList(List<Cart> cartList, Long itemId, Integer num );

    /**
     * 从redis中查询购物车
     * @param username
     * @return
     */
    public List<Cart> findCartListFromRedis(String username);

    /**
     * 从redis中查询购物车
     * @param username
     * @return
     */
    public void saveCartListToRedis(String username,List<Cart> cartList);
}
