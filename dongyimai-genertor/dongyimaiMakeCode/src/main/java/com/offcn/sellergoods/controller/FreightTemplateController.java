package com.offcn.sellergoods.controller;
import com.offcn.sellergoods.pojo.FreightTemplate;
import com.offcn.sellergoods.service.FreightTemplateService;
import com.offcn.entity.PageResult;
import com.offcn.entity.Result;
import com.offcn.entity.StatusCode;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/****
 * @Author:ujiuye
 * @Description:
 * @Date 2021/2/1 14:19
 *****/
@Api(tags = "FreightTemplateController")
@RestController
@RequestMapping("/freightTemplate")
@CrossOrigin
public class FreightTemplateController {

    @Autowired
    private FreightTemplateService freightTemplateService;

    /***
     * FreightTemplate分页条件搜索实现
     * @param freightTemplate
     * @param page
     * @param size
     * @return
     */
    @ApiOperation(value = "FreightTemplate条件分页查询",notes = "分页条件查询FreightTemplate方法详情",tags = {"FreightTemplateController"})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "page", value = "当前页", required = true),
            @ApiImplicitParam(paramType = "path", name = "size", value = "每页显示条数", required = true)
    })
    @PostMapping(value = "/search/{page}/{size}" )
    public Result<PageResult<FreightTemplate>> findPage(@RequestBody(required = false) @ApiParam(name = "FreightTemplate对象",value = "传入JSON数据",required = false) FreightTemplate freightTemplate, @PathVariable  int page, @PathVariable  int size){
        //调用FreightTemplateService实现分页条件查询FreightTemplate
        PageResult<FreightTemplate> pageResult = freightTemplateService.findPage(freightTemplate, page, size);
        return new Result(true,StatusCode.OK,"查询成功",pageResult);
    }

    /***
     * FreightTemplate分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @ApiOperation(value = "FreightTemplate分页查询",notes = "分页查询FreightTemplate方法详情",tags = {"FreightTemplateController"})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "page", value = "当前页", required = true),
            @ApiImplicitParam(paramType = "path", name = "size", value = "每页显示条数", required = true)
    })
    @GetMapping(value = "/search/{page}/{size}" )
    public Result<PageResult<FreightTemplate>> findPage(@PathVariable  int page, @PathVariable  int size){
        //调用FreightTemplateService实现分页查询FreightTemplate
        PageResult<FreightTemplate> pageResult = freightTemplateService.findPage(page, size);
        return new Result<PageResult<FreightTemplate>>(true,StatusCode.OK,"查询成功",pageResult);
    }

    /***
     * 多条件搜索品牌数据
     * @param freightTemplate
     * @return
     */
    @ApiOperation(value = "FreightTemplate条件查询",notes = "条件查询FreightTemplate方法详情",tags = {"FreightTemplateController"})
    @PostMapping(value = "/search" )
    public Result<List<FreightTemplate>> findList(@RequestBody(required = false) @ApiParam(name = "FreightTemplate对象",value = "传入JSON数据",required = false) FreightTemplate freightTemplate){
        //调用FreightTemplateService实现条件查询FreightTemplate
        List<FreightTemplate> list = freightTemplateService.findList(freightTemplate);
        return new Result<List<FreightTemplate>>(true,StatusCode.OK,"查询成功",list);
    }

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @ApiOperation(value = "FreightTemplate根据ID删除",notes = "根据ID删除FreightTemplate方法详情",tags = {"FreightTemplateController"})
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "Long")
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable Long id){
        //调用FreightTemplateService实现根据主键删除
        freightTemplateService.delete(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /***
     * 修改FreightTemplate数据
     * @param freightTemplate
     * @param id
     * @return
     */
    @ApiOperation(value = "FreightTemplate根据ID修改",notes = "根据ID修改FreightTemplate方法详情",tags = {"FreightTemplateController"})
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "Long")
    @PutMapping(value="/{id}")
    public Result update(@RequestBody @ApiParam(name = "FreightTemplate对象",value = "传入JSON数据",required = false) FreightTemplate freightTemplate,@PathVariable Long id){
        //设置主键值
        freightTemplate.setId(id);
        //调用FreightTemplateService实现修改FreightTemplate
        freightTemplateService.update(freightTemplate);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /***
     * 新增FreightTemplate数据
     * @param freightTemplate
     * @return
     */
    @ApiOperation(value = "FreightTemplate添加",notes = "添加FreightTemplate方法详情",tags = {"FreightTemplateController"})
    @PostMapping
    public Result add(@RequestBody  @ApiParam(name = "FreightTemplate对象",value = "传入JSON数据",required = true) FreightTemplate freightTemplate){
        //调用FreightTemplateService实现添加FreightTemplate
        freightTemplateService.add(freightTemplate);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    /***
     * 根据ID查询FreightTemplate数据
     * @param id
     * @return
     */
    @ApiOperation(value = "FreightTemplate根据ID查询",notes = "根据ID查询FreightTemplate方法详情",tags = {"FreightTemplateController"})
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "Long")
    @GetMapping("/{id}")
    public Result<FreightTemplate> findById(@PathVariable Long id){
        //调用FreightTemplateService实现根据主键查询FreightTemplate
        FreightTemplate freightTemplate = freightTemplateService.findById(id);
        return new Result<FreightTemplate>(true,StatusCode.OK,"查询成功",freightTemplate);
    }

    /***
     * 查询FreightTemplate全部数据
     * @return
     */
    @ApiOperation(value = "查询所有FreightTemplate",notes = "查询所FreightTemplate有方法详情",tags = {"FreightTemplateController"})
    @GetMapping
    public Result<List<FreightTemplate>> findAll(){
        //调用FreightTemplateService实现查询所有FreightTemplate
        List<FreightTemplate> list = freightTemplateService.findAll();
        return new Result<List<FreightTemplate>>(true, StatusCode.OK,"查询成功",list) ;
    }
}
