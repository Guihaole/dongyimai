package com.changgou.content.feign;
import com.offcn.entity.PageResult;
import com.offcn.entity.Result;
import com.offcn.sellergoods.pojo.SpecificationOption;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/****
 * @Author:ujiuye
 * @Description:
 * @Date 2021/2/1 14:19
 *****/
@FeignClient(name="sellergoods")
@RequestMapping("/specificationOption")
public interface SpecificationOptionFeign {

    /***
     * SpecificationOption分页条件搜索实现
     * @param specificationOption
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}" )
    Result<PageResult<SpecificationOption>> findPage(@RequestBody(required = false) SpecificationOption specificationOption, @PathVariable  int page, @PathVariable  int size);

    /***
     * SpecificationOption分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    Result<PageResult<SpecificationOption>> findPage(@PathVariable  int page, @PathVariable  int size);

    /***
     * 多条件搜索品牌数据
     * @param specificationOption
     * @return
     */
    @PostMapping(value = "/search" )
    Result<List<SpecificationOption>> findList(@RequestBody(required = false) SpecificationOption specificationOption);

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    Result delete(@PathVariable Long id);

    /***
     * 修改SpecificationOption数据
     * @param specificationOption
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    Result update(@RequestBody SpecificationOption specificationOption,@PathVariable Long id);

    /***
     * 新增SpecificationOption数据
     * @param specificationOption
     * @return
     */
    @PostMapping
    Result add(@RequestBody SpecificationOption specificationOption);

    /***
     * 根据ID查询SpecificationOption数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    Result<SpecificationOption> findById(@PathVariable Long id);

    /***
     * 查询SpecificationOption全部数据
     * @return
     */
    @GetMapping
    Result<List<SpecificationOption>> findAll();
}