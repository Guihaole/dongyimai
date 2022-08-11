package com.offcn.sellergoods.service;

import com.offcn.entity.PageResult;
import com.offcn.entity.Result;
import com.offcn.sellergoods.pojo.Brand;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/****
 * @Author:ujiuye
 * @Description:Brand业务层接口
 * @Date 2021/2/1 14:19
 *****/
public interface BrandService {
    public Result<PageResult<Brand>> findPage(Brand brand, int page, int size);

    public Result<PageResult<Brand>> findPage(int page,int size);

    public Result<List<Brand>> findList(Brand brand);

    public Result delete(Long id);

    public Result update(Brand brand,Long id);

    public Result add(Brand brand);

    public Result<Brand> findById(Long id);

    Result<List<Brand>> findAll();
}
