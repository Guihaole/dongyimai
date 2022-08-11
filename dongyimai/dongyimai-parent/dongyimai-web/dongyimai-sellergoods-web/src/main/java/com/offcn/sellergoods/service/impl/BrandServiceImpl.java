package com.offcn.sellergoods.service.impl;
import com.offcn.entity.Result;
import com.offcn.sellergoods.fegin.BrandFegin;
import com.offcn.sellergoods.pojo.Brand;
import com.offcn.sellergoods.service.BrandService;
import com.offcn.entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
/****
 * @Author:ujiuye
 * @Description:Brand业务层接口实现类
 * @Date 2021/2/1 14:19
 *****/
@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandFegin brandFegin;

    @Override
    public Result<PageResult<Brand>> findPage(Brand brand, int page, int size) {
        return brandFegin.findPage(brand, page, size);
    }

    @Override
    public Result<PageResult<Brand>> findPage(int page, int size) {
        return brandFegin.findPage(page, size);
    }

    @Override
    public Result<List<Brand>> findList(Brand brand) {
        return brandFegin.findList(brand);
    }

    @Override
    public Result delete(Long id) {
        return brandFegin.delete(id);
    }

    @Override
    public Result update(Brand brand, Long id) {
        return brandFegin.update(brand, id);
    }

    @Override
    public Result add(Brand brand) {
        return brandFegin.add(brand);
    }

    @Override
    public Result<Brand> findById(Long id) {
        return brandFegin.findById(id);
    }
    @Override
    public Result<List<Brand>> findAll() {
        return brandFegin.findAll();
    }
}
