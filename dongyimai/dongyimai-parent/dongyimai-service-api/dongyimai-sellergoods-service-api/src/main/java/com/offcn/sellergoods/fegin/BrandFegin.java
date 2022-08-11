package com.offcn.sellergoods.fegin;

import com.offcn.entity.PageResult;
import com.offcn.entity.Result;
import com.offcn.sellergoods.pojo.Brand;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "DYM-SELLERGOODS",path = "/brand")
public interface BrandFegin {
    @GetMapping
    public Result<List<Brand>> findAll();
    @PostMapping(value = "/search/{page}/{size}" )
    public Result<PageResult<Brand>> findPage(@RequestBody(required = false) Brand brand,
                                              @PathVariable(value = "page") int page,
                                              @PathVariable(value = "size")  int size);
    @GetMapping(value = "/search/{page}/{size}" )
    public Result<PageResult<Brand>> findPage(@PathVariable(value = "page")  int page, @PathVariable(value = "size")  int size);
    @PostMapping(value = "/search" )
    public Result<List<Brand>> findList(@RequestBody(required = false) Brand brand);
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable(value = "id") Long id);
    @PutMapping(value="/{id}")
    public Result update(@RequestBody Brand brand,@PathVariable(value = "id") Long id);
    @PostMapping
    public Result add(@RequestBody Brand brand);
    @GetMapping("/{id}")
    public Result<Brand> findById(@PathVariable(value = "id") Long id);
}
