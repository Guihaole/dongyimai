package com.offcn.user.fegin;
import com.offcn.entity.PageResult;
import com.offcn.entity.Result;

import com.offcn.user.pojo.Provinces;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/****
 * @Author:ujiuye
 * @Description:
 * @Date 2021/2/1 14:19
 *****/
@FeignClient(name="sellergoods")
@RequestMapping("/provinces")
public interface ProvincesFeign {

    /***
     * Provinces分页条件搜索实现
     * @param provinces
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}" )
    Result<PageResult<Provinces>> findPage(@RequestBody(required = false) Provinces provinces, @PathVariable  int page, @PathVariable  int size);

    /***
     * Provinces分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    Result<PageResult<Provinces>> findPage(@PathVariable  int page, @PathVariable  int size);

    /***
     * 多条件搜索品牌数据
     * @param provinces
     * @return
     */
    @PostMapping(value = "/search" )
    Result<List<Provinces>> findList(@RequestBody(required = false) Provinces provinces);

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    Result delete(@PathVariable Integer id);

    /***
     * 修改Provinces数据
     * @param provinces
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    Result update(@RequestBody Provinces provinces,@PathVariable Integer id);

    /***
     * 新增Provinces数据
     * @param provinces
     * @return
     */
    @PostMapping
    Result add(@RequestBody Provinces provinces);

    /***
     * 根据ID查询Provinces数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    Result<Provinces> findById(@PathVariable Integer id);

    /***
     * 查询Provinces全部数据
     * @return
     */
    @GetMapping
    Result<List<Provinces>> findAll();
}
