package com.offcn.sellergoods.service.impl;

import com.offcn.sellergoods.dao.SpecificationMapper;
import com.offcn.sellergoods.dao.SpecificationOptionMapper;
import com.offcn.sellergoods.entity.SpecEntity;
import com.offcn.sellergoods.pojo.Specification;
import com.offcn.sellergoods.pojo.SpecificationOption;
import com.offcn.sellergoods.service.SpecificationService;
import com.offcn.entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;
import java.util.Map;

/****
 * @Author:ujiuye
 * @Description:Specification业务层接口实现类
 * @Date 2021/2/1 14:19
 *****/
@Service
public class SpecificationServiceImpl extends ServiceImpl<SpecificationMapper, Specification> implements SpecificationService {


    @Autowired
    private SpecificationMapper specificationMapper;
    @Autowired
    private SpecificationOptionMapper specificationOptionMapper;

    /**
     * Specification条件+分页查询
     *
     * @param specification 查询条件
     * @param page          页码
     * @param size          页大小
     * @return 分页结果
     */
    @Override
    public PageResult<Specification> findPage(Specification specification, int page, int size) {
        Page<Specification> mypage = new Page<>(page, size);
        QueryWrapper<Specification> queryWrapper = this.createQueryWrapper(specification);
        IPage<Specification> iPage = this.page(mypage, queryWrapper);
        return new PageResult<Specification>(iPage.getTotal(), iPage.getRecords());
    }

    /**
     * Specification分页查询
     *
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageResult<Specification> findPage(int page, int size) {
        Page<Specification> mypage = new Page<>(page, size);
        IPage<Specification> iPage = this.page(mypage, new QueryWrapper<Specification>());

        return new PageResult<Specification>(iPage.getTotal(), iPage.getRecords());
    }

    /**
     * Specification条件查询
     *
     * @param specification
     * @return
     */
    @Override
    public List<Specification> findList(Specification specification) {
        //构建查询条件
        QueryWrapper<Specification> queryWrapper = this.createQueryWrapper(specification);
        //根据构建的条件查询数据
        return this.list(queryWrapper);
    }


    /**
     * Specification构建查询对象
     *
     * @param specification
     * @return
     */
    public QueryWrapper<Specification> createQueryWrapper(Specification specification) {
        QueryWrapper<Specification> queryWrapper = new QueryWrapper<>();
        if (specification != null) {
            // 主键
            if (!StringUtils.isEmpty(specification.getId())) {
                queryWrapper.eq("id", specification.getId());
            }
            // 名称
            if (!StringUtils.isEmpty(specification.getSpecName())) {
                queryWrapper.eq("spec_name", specification.getSpecName());
            }
        }
        return queryWrapper;
    }

    /**
     * 删除
     *
     * @param id
     */
    @Transactional
    @Override
    public void delete(Long id) {
        //1.根据id删除规格项表
        QueryWrapper<SpecificationOption> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("spec_id",id);
        int delete = specificationOptionMapper.delete(queryWrapper);
        //2. 删除规格表
        specificationMapper.deleteById(id);
    }

    /**
     * 修改Specification
     *
     * @param specEntity
     */
    @Transactional
    @Override
    public void update(SpecEntity specEntity, Long id) {
        //1.根据id修改规格表
        Specification specification = specEntity.getSpecification();
        specification.setId(id);
        int update = specificationMapper.updateById(specification);
        //2.根据id删除规格项表
        QueryWrapper<SpecificationOption> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("spec_id", id);
        int delete = specificationOptionMapper.delete(queryWrapper);
        //3.新增规格项表
        List<SpecificationOption> optionList = specEntity.getSpecificationOptionList();
        if (optionList != null && optionList.size() > 0) {
            for (SpecificationOption option : optionList) {
                option.setSpecId(id);
                specificationOptionMapper.insert(option);
            }
        }
    }

    /**
     * 增加Specification
     *
     * @param specEntity
     */
    @Transactional
    @Override
    public void add(SpecEntity specEntity) {
        //1.保存规格
        int insert = specificationMapper.insert(specEntity.getSpecification());
        //2.保存规格项
        List<SpecificationOption> optionList = specEntity.getSpecificationOptionList();
        for (SpecificationOption option : optionList) {
            option.setSpecId(specEntity.getSpecification().getId());
            specificationOptionMapper.insert(option);
        }
    }

    /**
     * 根据ID查询SpecEntity
     *
     * @param id
     * @return
     */
    @Override
    public SpecEntity findById(Long id) {
        //1.根据id查询规格
        Specification specification = specificationMapper.selectById(id);
        //2. 根据id查询规格项
        QueryWrapper<SpecificationOption> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("spec_id", id);
        List<SpecificationOption> specificationOptionList = specificationOptionMapper.selectList(queryWrapper);
        SpecEntity specEntity = new SpecEntity(specification, specificationOptionList);
        return specEntity;
    }

    /**
     * 查询Specification全部数据
     *
     * @return
     */
    @Override
    public List<Specification> findAll() {
        return this.list(new QueryWrapper<Specification>());
    }
    /**
     * 查询规格下拉列表
     *
     * @return
     */
    @Override
    public List<Map> selectOptions() {
        return specificationMapper.selectOptions();
    }
}
