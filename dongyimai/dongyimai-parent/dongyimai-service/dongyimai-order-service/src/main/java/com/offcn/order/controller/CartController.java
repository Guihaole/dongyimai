package com.offcn.order.controller;

import com.offcn.entity.Result;
import com.offcn.entity.StatusCode;
import com.offcn.order.pojo.Cart;
import com.offcn.order.service.CartService;
import com.offcn.utils.TokenDecode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cart")
@CrossOrigin
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private TokenDecode tokenDecode;

    /**
     * 展示购物车
     * 1. 从redis中获取cartList---->ujiuye得数据  前端传给得是username
     */
    @GetMapping("/findCartList")
    public List<Cart> findCartList() {
        Map<String, String> userInfo = tokenDecode.getUserInfo();
        System.out.println(userInfo);
        String username=userInfo.get("username");
        if (username==null||username.equals("")) {
            username = "ujiuye";
        }
        return cartService.findCartListFromRedis(username);//从redis中提取
    }
    /**
     *  添加购物车  itemid ,num
     *  1.首先查询有没有对应的购物车cartList
     *  2. 如果没有就创建cartList  cart item
     *  2.1 如果有判断有没有对应得cart
     *  2.2  如果没有创建对应得cart item
     *  2.1.1 判断有没有对应得商品 数量增加
     *  2.1.2 判断没有创建item
     */
    @GetMapping("/addGoodsToCartList")
    public Result addGoodsToCartList(Long itemId, Integer num) {
        try {
            Map<String, String> userInfo = tokenDecode.getUserInfo();
            System.out.println(userInfo);
            String username=userInfo.get("username");
            if (username==null||username.equals("")) {
                username = "ujiuye";
            }
            //调用业务层方法
            List<Cart> cartList = cartService.findCartListFromRedis(username);
            cartList = cartService.addGoodsToCartList(cartList, itemId, num);
            cartService.saveCartListToRedis(username,cartList);
            return new Result(true, StatusCode.OK,"添加购物车成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR,"添加购物车失败");
        }
    }
}
