package com.changgou.content.feign;
import com.offcn.entity.PageResult;
import com.offcn.entity.Result;
import com.offcn.sellergoods.pojo.Specification;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/****
 * @Author:ujiuye
 * @Description:
 * @Date 2021/2/1 14:19
 *****/
@FeignClient(name="sellergoods")
@RequestMapping("/specification")
public interface SpecificationFeign {

    /***
     * Specification分页条件搜索实现
     * @param specification
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}" )
    Result<PageResult<Specification>> findPage(@RequestBody(required = false) Specification specification, @PathVariable  int page, @PathVariable  int size);

    /***
     * Specification分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    Result<PageResult<Specification>> findPage(@PathVariable  int page, @PathVariable  int size);

    /***
     * 多条件搜索品牌数据
     * @param specification
     * @return
     */
    @PostMapping(value = "/search" )
    Result<List<Specification>> findList(@RequestBody(required = false) Specification specification);

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    Result delete(@PathVariable Long id);

    /***
     * 修改Specification数据
     * @param specification
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    Result update(@RequestBody Specification specification,@PathVariable Long id);

    /***
     * 新增Specification数据
     * @param specification
     * @return
     */
    @PostMapping
    Result add(@RequestBody Specification specification);

    /***
     * 根据ID查询Specification数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    Result<Specification> findById(@PathVariable Long id);

    /***
     * 查询Specification全部数据
     * @return
     */
    @GetMapping
    Result<List<Specification>> findAll();
}