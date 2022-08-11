package com.changgou.content.feign;
import com.offcn.entity.PageResult;
import com.offcn.entity.Result;
import com.offcn.sellergoods.pojo.ItemCat;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/****
 * @Author:ujiuye
 * @Description:
 * @Date 2021/2/1 14:19
 *****/
@FeignClient(name="sellergoods")
@RequestMapping("/itemCat")
public interface ItemCatFeign {

    /***
     * ItemCat分页条件搜索实现
     * @param itemCat
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}" )
    Result<PageResult<ItemCat>> findPage(@RequestBody(required = false) ItemCat itemCat, @PathVariable  int page, @PathVariable  int size);

    /***
     * ItemCat分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    Result<PageResult<ItemCat>> findPage(@PathVariable  int page, @PathVariable  int size);

    /***
     * 多条件搜索品牌数据
     * @param itemCat
     * @return
     */
    @PostMapping(value = "/search" )
    Result<List<ItemCat>> findList(@RequestBody(required = false) ItemCat itemCat);

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    Result delete(@PathVariable Long id);

    /***
     * 修改ItemCat数据
     * @param itemCat
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    Result update(@RequestBody ItemCat itemCat,@PathVariable Long id);

    /***
     * 新增ItemCat数据
     * @param itemCat
     * @return
     */
    @PostMapping
    Result add(@RequestBody ItemCat itemCat);

    /***
     * 根据ID查询ItemCat数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    Result<ItemCat> findById(@PathVariable Long id);

    /***
     * 查询ItemCat全部数据
     * @return
     */
    @GetMapping
    Result<List<ItemCat>> findAll();
}