package com.offcn.sellergoods.service.impl;
import com.offcn.entity.Result;
import com.offcn.entity.StatusCode;
import com.offcn.sellergoods.dao.BrandMapper;
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
import java.util.Map;

/****
 * @Author:ujiuye
 * @Description:Brand业务层接口实现类
 * @Date 2021/2/1 14:19
 *****/
@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper,Brand> implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    /**
     * Brand条件+分页查询
     * @param brand 查询条件
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageResult<Brand> findPage(Brand brand, int page, int size){
         Page<Brand> mypage = new Page<>(page, size);
        QueryWrapper<Brand> queryWrapper = this.createQueryWrapper(brand);
        IPage<Brand> iPage = this.page(mypage, queryWrapper);
        return new PageResult<Brand>(iPage.getTotal(),iPage.getRecords());
    }

    /**
     * Brand分页查询
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageResult<Brand> findPage(int page, int size){
        Page<Brand> mypage = new Page<>(page, size);
        IPage<Brand> iPage = this.page(mypage, new QueryWrapper<Brand>());

        return new PageResult<Brand>(iPage.getTotal(),iPage.getRecords());
    }

    /**
     * Brand条件查询
     * @param brand
     * @return
     */
    @Override
    public List<Brand> findList(Brand brand){
        //构建查询条件
        QueryWrapper<Brand> queryWrapper = this.createQueryWrapper(brand);
        //根据构建的条件查询数据
        return this.list(queryWrapper);
    }


    /**
     * Brand构建查询对象
     * @param brand
     * @return
     */
    public QueryWrapper<Brand> createQueryWrapper(Brand brand){
        QueryWrapper<Brand> queryWrapper = new QueryWrapper<>();
        if(brand!=null){
            //
            if(!StringUtils.isEmpty(brand.getId())){
                 queryWrapper.eq("id",brand.getId());
            }
            // 品牌名称
            if(!StringUtils.isEmpty(brand.getName())){
                queryWrapper.like("name",brand.getName());
            }
            // 品牌首字母
            if(!StringUtils.isEmpty(brand.getFirstChar())){
                 queryWrapper.eq("first_char",brand.getFirstChar());
            }
            // 品牌图像
            if(!StringUtils.isEmpty(brand.getImage())){
                 queryWrapper.eq("image",brand.getImage());
            }
        }
        return queryWrapper;
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(Long id){
        this.removeById(id);
    }

    /**
     * 修改Brand
     * @param brand
     */
    @Override
    public void update(Brand brand){
        this.updateById(brand);
    }

    /**
     * 增加Brand
     * @param brand
     */
    @Override
    public void add(Brand brand){
        this.save(brand);
    }

    /**
     * 根据ID查询Brand
     * @param id
     * @return
     */
    @Override
    public Brand findById(Long id){
        return  this.getById(id);
    }

    /**
     * 查询Brand全部数据
     * @return
     */
    @Override
    public List<Brand> findAll() {
        return this.list(new QueryWrapper<Brand>());
    }
    //查询tb_type_template表中的数据返回list集合
    @Override
    public Result<List<Map>> selectOptions() {
        List<Map> mapList = brandMapper.selectTypeTemplateMap();
        Result<List<Map>> result = new Result<>();
        result.setFlag(true);
        result.setCode(StatusCode.OK);
        result.setData(mapList);
        result.setMessage("查询数据成功");
        return result;
    }

}
