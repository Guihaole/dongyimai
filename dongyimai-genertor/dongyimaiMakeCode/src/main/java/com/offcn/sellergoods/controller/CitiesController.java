package com.offcn.sellergoods.controller;
import com.offcn.sellergoods.pojo.Cities;
import com.offcn.sellergoods.service.CitiesService;
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
@Api(tags = "CitiesController")
@RestController
@RequestMapping("/cities")
@CrossOrigin
public class CitiesController {

    @Autowired
    private CitiesService citiesService;

    /***
     * Cities分页条件搜索实现
     * @param cities
     * @param page
     * @param size
     * @return
     */
    @ApiOperation(value = "Cities条件分页查询",notes = "分页条件查询Cities方法详情",tags = {"CitiesController"})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "page", value = "当前页", required = true),
            @ApiImplicitParam(paramType = "path", name = "size", value = "每页显示条数", required = true)
    })
    @PostMapping(value = "/search/{page}/{size}" )
    public Result<PageResult<Cities>> findPage(@RequestBody(required = false) @ApiParam(name = "Cities对象",value = "传入JSON数据",required = false) Cities cities, @PathVariable  int page, @PathVariable  int size){
        //调用CitiesService实现分页条件查询Cities
        PageResult<Cities> pageResult = citiesService.findPage(cities, page, size);
        return new Result(true,StatusCode.OK,"查询成功",pageResult);
    }

    /***
     * Cities分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @ApiOperation(value = "Cities分页查询",notes = "分页查询Cities方法详情",tags = {"CitiesController"})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "page", value = "当前页", required = true),
            @ApiImplicitParam(paramType = "path", name = "size", value = "每页显示条数", required = true)
    })
    @GetMapping(value = "/search/{page}/{size}" )
    public Result<PageResult<Cities>> findPage(@PathVariable  int page, @PathVariable  int size){
        //调用CitiesService实现分页查询Cities
        PageResult<Cities> pageResult = citiesService.findPage(page, size);
        return new Result<PageResult<Cities>>(true,StatusCode.OK,"查询成功",pageResult);
    }

    /***
     * 多条件搜索品牌数据
     * @param cities
     * @return
     */
    @ApiOperation(value = "Cities条件查询",notes = "条件查询Cities方法详情",tags = {"CitiesController"})
    @PostMapping(value = "/search" )
    public Result<List<Cities>> findList(@RequestBody(required = false) @ApiParam(name = "Cities对象",value = "传入JSON数据",required = false) Cities cities){
        //调用CitiesService实现条件查询Cities
        List<Cities> list = citiesService.findList(cities);
        return new Result<List<Cities>>(true,StatusCode.OK,"查询成功",list);
    }

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @ApiOperation(value = "Cities根据ID删除",notes = "根据ID删除Cities方法详情",tags = {"CitiesController"})
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "Integer")
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable Integer id){
        //调用CitiesService实现根据主键删除
        citiesService.delete(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /***
     * 修改Cities数据
     * @param cities
     * @param id
     * @return
     */
    @ApiOperation(value = "Cities根据ID修改",notes = "根据ID修改Cities方法详情",tags = {"CitiesController"})
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "Integer")
    @PutMapping(value="/{id}")
    public Result update(@RequestBody @ApiParam(name = "Cities对象",value = "传入JSON数据",required = false) Cities cities,@PathVariable Integer id){
        //设置主键值
        cities.setId(id);
        //调用CitiesService实现修改Cities
        citiesService.update(cities);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /***
     * 新增Cities数据
     * @param cities
     * @return
     */
    @ApiOperation(value = "Cities添加",notes = "添加Cities方法详情",tags = {"CitiesController"})
    @PostMapping
    public Result add(@RequestBody  @ApiParam(name = "Cities对象",value = "传入JSON数据",required = true) Cities cities){
        //调用CitiesService实现添加Cities
        citiesService.add(cities);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    /***
     * 根据ID查询Cities数据
     * @param id
     * @return
     */
    @ApiOperation(value = "Cities根据ID查询",notes = "根据ID查询Cities方法详情",tags = {"CitiesController"})
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "Integer")
    @GetMapping("/{id}")
    public Result<Cities> findById(@PathVariable Integer id){
        //调用CitiesService实现根据主键查询Cities
        Cities cities = citiesService.findById(id);
        return new Result<Cities>(true,StatusCode.OK,"查询成功",cities);
    }

    /***
     * 查询Cities全部数据
     * @return
     */
    @ApiOperation(value = "查询所有Cities",notes = "查询所Cities有方法详情",tags = {"CitiesController"})
    @GetMapping
    public Result<List<Cities>> findAll(){
        //调用CitiesService实现查询所有Cities
        List<Cities> list = citiesService.findAll();
        return new Result<List<Cities>>(true, StatusCode.OK,"查询成功",list) ;
    }
}
