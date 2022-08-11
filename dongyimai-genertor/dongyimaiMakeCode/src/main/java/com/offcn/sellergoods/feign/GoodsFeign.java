package com.changgou.content.feign;
import com.offcn.entity.PageResult;
import com.offcn.entity.Result;
import com.offcn.sellergoods.pojo.Goods;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/****
 * @Author:ujiuye
 * @Description:
 * @Date 2021/2/1 14:19
 *****/
@FeignClient(name="sellergoods")
@RequestMapping("/goods")
public interface GoodsFeign {

    /***
     * Goods分页条件搜索实现
     * @param goods
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}" )
    Result<PageResult<Goods>> findPage(@RequestBody(required = false) Goods goods, @PathVariable  int page, @PathVariable  int size);

    /***
     * Goods分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    Result<PageResult<Goods>> findPage(@PathVariable  int page, @PathVariable  int size);

    /***
     * 多条件搜索品牌数据
     * @param goods
     * @return
     */
    @PostMapping(value = "/search" )
    Result<List<Goods>> findList(@RequestBody(required = false) Goods goods);

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    Result delete(@PathVariable Long id);

    /***
     * 修改Goods数据
     * @param goods
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    Result update(@RequestBody Goods goods,@PathVariable Long id);

    /***
     * 新增Goods数据
     * @param goods
     * @return
     */
    @PostMapping
    Result add(@RequestBody Goods goods);

    /***
     * 根据ID查询Goods数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    Result<Goods> findById(@PathVariable Long id);

    /***
     * 查询Goods全部数据
     * @return
     */
    @GetMapping
    Result<List<Goods>> findAll();
}