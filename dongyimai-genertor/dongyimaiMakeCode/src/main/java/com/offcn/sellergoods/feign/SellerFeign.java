package com.changgou.content.feign;
import com.offcn.entity.PageResult;
import com.offcn.entity.Result;
import com.offcn.sellergoods.pojo.Seller;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/****
 * @Author:ujiuye
 * @Description:
 * @Date 2021/2/1 14:19
 *****/
@FeignClient(name="sellergoods")
@RequestMapping("/seller")
public interface SellerFeign {

    /***
     * Seller分页条件搜索实现
     * @param seller
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}" )
    Result<PageResult<Seller>> findPage(@RequestBody(required = false) Seller seller, @PathVariable  int page, @PathVariable  int size);

    /***
     * Seller分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    Result<PageResult<Seller>> findPage(@PathVariable  int page, @PathVariable  int size);

    /***
     * 多条件搜索品牌数据
     * @param seller
     * @return
     */
    @PostMapping(value = "/search" )
    Result<List<Seller>> findList(@RequestBody(required = false) Seller seller);

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    Result delete(@PathVariable String id);

    /***
     * 修改Seller数据
     * @param seller
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    Result update(@RequestBody Seller seller,@PathVariable String id);

    /***
     * 新增Seller数据
     * @param seller
     * @return
     */
    @PostMapping
    Result add(@RequestBody Seller seller);

    /***
     * 根据ID查询Seller数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    Result<Seller> findById(@PathVariable String id);

    /***
     * 查询Seller全部数据
     * @return
     */
    @GetMapping
    Result<List<Seller>> findAll();
}