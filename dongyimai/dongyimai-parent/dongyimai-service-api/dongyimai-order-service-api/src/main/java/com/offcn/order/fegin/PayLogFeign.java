package com.offcn.order.fegin;
import com.offcn.entity.PageResult;
import com.offcn.entity.Result;

import com.offcn.order.pojo.PayLog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/****
 * @Author:ujiuye
 * @Description:
 * @Date 2021/2/1 14:19
 *****/
@FeignClient(name="order",path = "/payLog")
public interface PayLogFeign {

    /**
     * 修改订单状态
     * @param out_trade_no
     * @param transaction_id
     * @return
     */
    @GetMapping(value = "/updateOrderStatus")
    public Result updateOrderStatus(
            @RequestParam(value="out_trade_no")  String out_trade_no, @RequestParam(value="transaction_id") String transaction_id);
//    /***
//     * PayLog分页条件搜索实现
//     * @param payLog
//     * @param page
//     * @param size
//     * @return
//     */
//    @PostMapping(value = "/search/{page}/{size}" )
//    Result<PageResult<PayLog>> findPage(@RequestBody(required = false) PayLog payLog, @PathVariable  int page, @PathVariable  int size);
//
//    /***
//     * PayLog分页搜索实现
//     * @param page:当前页
//     * @param size:每页显示多少条
//     * @return
//     */
//    @GetMapping(value = "/search/{page}/{size}" )
//    Result<PageResult<PayLog>> findPage(@PathVariable  int page, @PathVariable  int size);
//
//    /***
//     * 多条件搜索品牌数据
//     * @param payLog
//     * @return
//     */
//    @PostMapping(value = "/search" )
//    Result<List<PayLog>> findList(@RequestBody(required = false) PayLog payLog);
//
//    /***
//     * 根据ID删除品牌数据
//     * @param id
//     * @return
//     */
//    @DeleteMapping(value = "/{id}" )
//    Result delete(@PathVariable String id);
//
//    /***
//     * 修改PayLog数据
//     * @param payLog
//     * @param id
//     * @return
//     */
//    @PutMapping(value="/{id}")
//    Result update(@RequestBody PayLog payLog,@PathVariable String id);
//
//    /***
//     * 新增PayLog数据
//     * @param payLog
//     * @return
//     */
//    @PostMapping
//    Result add(@RequestBody PayLog payLog);
//
//    /***
//     * 根据ID查询PayLog数据
//     * @param id
//     * @return
//     */
//    @GetMapping("/{id}")
//    Result<PayLog> findById(@PathVariable String id);
//
//    /***
//     * 查询PayLog全部数据
//     * @return
//     */
//    @GetMapping
//    Result<List<PayLog>> findAll();
}
