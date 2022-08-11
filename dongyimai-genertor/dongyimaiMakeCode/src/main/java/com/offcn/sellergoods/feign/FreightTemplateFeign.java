package com.changgou.content.feign;
import com.offcn.entity.PageResult;
import com.offcn.entity.Result;
import com.offcn.sellergoods.pojo.FreightTemplate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/****
 * @Author:ujiuye
 * @Description:
 * @Date 2021/2/1 14:19
 *****/
@FeignClient(name="sellergoods")
@RequestMapping("/freightTemplate")
public interface FreightTemplateFeign {

    /***
     * FreightTemplate分页条件搜索实现
     * @param freightTemplate
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}" )
    Result<PageResult<FreightTemplate>> findPage(@RequestBody(required = false) FreightTemplate freightTemplate, @PathVariable  int page, @PathVariable  int size);

    /***
     * FreightTemplate分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    Result<PageResult<FreightTemplate>> findPage(@PathVariable  int page, @PathVariable  int size);

    /***
     * 多条件搜索品牌数据
     * @param freightTemplate
     * @return
     */
    @PostMapping(value = "/search" )
    Result<List<FreightTemplate>> findList(@RequestBody(required = false) FreightTemplate freightTemplate);

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    Result delete(@PathVariable Long id);

    /***
     * 修改FreightTemplate数据
     * @param freightTemplate
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    Result update(@RequestBody FreightTemplate freightTemplate,@PathVariable Long id);

    /***
     * 新增FreightTemplate数据
     * @param freightTemplate
     * @return
     */
    @PostMapping
    Result add(@RequestBody FreightTemplate freightTemplate);

    /***
     * 根据ID查询FreightTemplate数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    Result<FreightTemplate> findById(@PathVariable Long id);

    /***
     * 查询FreightTemplate全部数据
     * @return
     */
    @GetMapping
    Result<List<FreightTemplate>> findAll();
}