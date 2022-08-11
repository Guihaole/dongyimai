package com.offcn.sellergoods.controller;

import com.offcn.entity.PageResult;
import com.offcn.entity.Result;
import com.offcn.entity.StatusCode;
import com.offcn.sellergoods.pojo.Brand;
import com.offcn.sellergoods.service.BrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/****
 * @Author:ujiuye
 * @Description:
 * @Date 2021/2/1 14:19
 *****/
@Api(tags = "BrandController")
@RestController
@RequestMapping("/brand")
@CrossOrigin
public class BrandController {
    @Autowired
    private BrandService brandService;

    @ApiOperation(value = "查询所有Brand", notes = "查询所Brand有方法详情", tags = {"BrandController"})
    @GetMapping
    public Result<List<Brand>> findAll() {
        return brandService.findAll();
    }

    @PostMapping(value = "/search/{page}/{size}")
    public Result<PageResult<Brand>> findPage(@RequestBody(required = false) Brand brand,
                                              @PathVariable(value = "page") int page,
                                              @PathVariable(value = "size") int size) {
        return brandService.findPage(brand, page, size);
    }

    @GetMapping(value = "/search/{page}/{size}")
    public Result<PageResult<Brand>> findPage(@PathVariable(value = "page") int page,
                                              @PathVariable(value = "size") int size) {
        return brandService.findPage(page, size);
    }

    @PostMapping(value = "/search")
    public Result<List<Brand>> findList(@RequestBody(required = false) Brand brand) {
        return brandService.findList(brand);
    }

    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable(value = "id") Long id) {
        return brandService.delete(id);
    }

    @PutMapping(value = "/{id}")
    public Result update(@RequestBody Brand brand, @PathVariable(value = "id") Long id) {
        return brandService.update(brand, id);
    }

    @PostMapping
    public Result add(@RequestBody Brand brand) {
        return brandService.add(brand);
    }

    @GetMapping("/{id}")
    public Result<Brand> findById(@PathVariable(value = "id") Long id) {
        return brandService.findById(id);
    }
}
