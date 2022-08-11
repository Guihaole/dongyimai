package com.offcn.content.controller;

import com.offcn.content.pojo.Content;
import com.offcn.content.service.ContentService;
import com.offcn.entity.PageResult;
import com.offcn.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/content")
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
    @PostMapping(value = "/search/{page}/{size}" )
    Result<PageResult<Content>> findPage(@RequestBody(required = false) Content content,
                                         @PathVariable("page")  int page,
                                         @PathVariable("size")  int size){
       return contentService.findPage(content, page, size);
    };

    /***
     * Content分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    Result<PageResult<Content>> findPage(@PathVariable("page")  int page,
                                         @PathVariable("size")  int size){
        return contentService.findPage(page, size);
    };

    /***
     * 多条件搜索品牌数据
     * @param content
     * @return
     */
    @PostMapping(value = "/search" )
    Result<List<Content>> findList(@RequestBody(required = false) Content content){
        return contentService.findList(content);
    };

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    Result delete(@PathVariable("id") Long id){
        return contentService.delete(id);
    };

    /***
     * 修改Content数据
     * @param content
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    Result update(@RequestBody Content content,@PathVariable("id") Long id){
        return contentService.update(content, id);
    };

    /***
     * 新增Content数据
     * @param content
     * @return
     */
    @PostMapping
    Result add(@RequestBody Content content){
        return contentService.add(content);
    };

    /***
     * 根据ID查询Content数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    Result<Content> findById(@PathVariable("id") Long id){
        return contentService.findById(id);
    };

    /***
     * 查询Content全部数据
     * @return
     */
    @GetMapping
    Result<List<Content>> findAll(){
        return contentService.findAll();
    };
    /***
     * 根据分类ID查询所有广告
     */
    @GetMapping(value = "/list/category/{id}")
    Result<List<Content>> findByCategory(@PathVariable("id") Long id){
        return contentService.findByCategory(id);
    };
}
