package com.changgou.content.feign;
import com.offcn.entity.PageResult;
import com.offcn.entity.Result;
import com.offcn.sellergoods.pojo.GoodsDesc;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/****
 * @Author:ujiuye
 * @Description:
 * @Date 2021/2/1 14:19
 *****/
@FeignClient(name="sellergoods")
@RequestMapping("/goodsDesc")
public interface GoodsDescFeign {

    /***
     * GoodsDesc分页条件搜索实现
     * @param goodsDesc
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}" )
    Result<PageResult<GoodsDesc>> findPage(@RequestBody(required = false) GoodsDesc goodsDesc, @PathVariable  int page, @PathVariable  int size);

    /***
     * GoodsDesc分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    Result<PageResult<GoodsDesc>> findPage(@PathVariable  int page, @PathVariable  int size);

    /***
     * 多条件搜索品牌数据
     * @param goodsDesc
     * @return
     */
    @PostMapping(value = "/search" )
    Result<List<GoodsDesc>> findList(@RequestBody(required = false) GoodsDesc goodsDesc);

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    Result delete(@PathVariable Long id);

    /***
     * 修改GoodsDesc数据
     * @param goodsDesc
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    Result update(@RequestBody GoodsDesc goodsDesc,@PathVariable Long id);

    /***
     * 新增GoodsDesc数据
     * @param goodsDesc
     * @return
     */
    @PostMapping
    Result add(@RequestBody GoodsDesc goodsDesc);

    /***
     * 根据ID查询GoodsDesc数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    Result<GoodsDesc> findById(@PathVariable Long id);

    /***
     * 查询GoodsDesc全部数据
     * @return
     */
    @GetMapping
    Result<List<GoodsDesc>> findAll();
}