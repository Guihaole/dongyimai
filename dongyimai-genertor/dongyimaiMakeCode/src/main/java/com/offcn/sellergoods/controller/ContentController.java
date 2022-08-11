package com.offcn.sellergoods.controller;
import com.offcn.sellergoods.pojo.Content;
import com.offcn.sellergoods.service.ContentService;
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
@Api(tags = "ContentController")
@RestController
@RequestMapping("/content")
@CrossOrigin
public class ContentController {

    @Autowired
    private ContentService contentService;

    /***
     * Content分页条件搜索实现
     * @param content
     * @param page
     * @param size
     * @return
     */
    @ApiOperation(value = "Content条件分页查询",notes = "分页条件查询Content方法详情",tags = {"ContentController"})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "page", value = "当前页", required = true),
            @ApiImplicitParam(paramType = "path", name = "size", value = "每页显示条数", required = true)
    })
    @PostMapping(value = "/search/{page}/{size}" )
    public Result<PageResult<Content>> findPage(@RequestBody(required = false) @ApiParam(name = "Content对象",value = "传入JSON数据",required = false) Content content, @PathVariable  int page, @PathVariable  int size){
        //调用ContentService实现分页条件查询Content
        PageResult<Content> pageResult = contentService.findPage(content, page, size);
        return new Result(true,StatusCode.OK,"查询成功",pageResult);
    }

    /***
     * Content分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @ApiOperation(value = "Content分页查询",notes = "分页查询Content方法详情",tags = {"ContentController"})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "page", value = "当前页", required = true),
            @ApiImplicitParam(paramType = "path", name = "size", value = "每页显示条数", required = true)
    })
    @GetMapping(value = "/search/{page}/{size}" )
    public Result<PageResult<Content>> findPage(@PathVariable  int page, @PathVariable  int size){
        //调用ContentService实现分页查询Content
        PageResult<Content> pageResult = contentService.findPage(page, size);
        return new Result<PageResult<Content>>(true,StatusCode.OK,"查询成功",pageResult);
    }

    /***
     * 多条件搜索品牌数据
     * @param content
     * @return
     */
    @ApiOperation(value = "Content条件查询",notes = "条件查询Content方法详情",tags = {"ContentController"})
    @PostMapping(value = "/search" )
    public Result<List<Content>> findList(@RequestBody(required = false) @ApiParam(name = "Content对象",value = "传入JSON数据",required = false) Content content){
        //调用ContentService实现条件查询Content
        List<Content> list = contentService.findList(content);
        return new Result<List<Content>>(true,StatusCode.OK,"查询成功",list);
    }

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @ApiOperation(value = "Content根据ID删除",notes = "根据ID删除Content方法详情",tags = {"ContentController"})
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "Long")
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable Long id){
        //调用ContentService实现根据主键删除
        contentService.delete(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /***
     * 修改Content数据
     * @param content
     * @param id
     * @return
     */
    @ApiOperation(value = "Content根据ID修改",notes = "根据ID修改Content方法详情",tags = {"ContentController"})
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "Long")
    @PutMapping(value="/{id}")
    public Result update(@RequestBody @ApiParam(name = "Content对象",value = "传入JSON数据",required = false) Content content,@PathVariable Long id){
        //设置主键值
        content.setId(id);
        //调用ContentService实现修改Content
        contentService.update(content);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /***
     * 新增Content数据
     * @param content
     * @return
     */
    @ApiOperation(value = "Content添加",notes = "添加Content方法详情",tags = {"ContentController"})
    @PostMapping
    public Result add(@RequestBody  @ApiParam(name = "Content对象",value = "传入JSON数据",required = true) Content content){
        //调用ContentService实现添加Content
        contentService.add(content);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    /***
     * 根据ID查询Content数据
     * @param id
     * @return
     */
    @ApiOperation(value = "Content根据ID查询",notes = "根据ID查询Content方法详情",tags = {"ContentController"})
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "Long")
    @GetMapping("/{id}")
    public Result<Content> findById(@PathVariable Long id){
        //调用ContentService实现根据主键查询Content
        Content content = contentService.findById(id);
        return new Result<Content>(true,StatusCode.OK,"查询成功",content);
    }

    /***
     * 查询Content全部数据
     * @return
     */
    @ApiOperation(value = "查询所有Content",notes = "查询所Content有方法详情",tags = {"ContentController"})
    @GetMapping
    public Result<List<Content>> findAll(){
        //调用ContentService实现查询所有Content
        List<Content> list = contentService.findAll();
        return new Result<List<Content>>(true, StatusCode.OK,"查询成功",list) ;
    }
}
