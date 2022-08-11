package com.offcn.sellergoods.service.impl;
import com.offcn.sellergoods.dao.FreightTemplateMapper;
import com.offcn.sellergoods.pojo.FreightTemplate;
import com.offcn.sellergoods.service.FreightTemplateService;
import com.github.pagehelper.PageHelper;
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
 * @Description:FreightTemplate业务层接口实现类
 * @Date 2021/2/1 14:19
 *****/
@Service
public class FreightTemplateServiceImpl extends ServiceImpl<FreightTemplateMapper,FreightTemplate> implements FreightTemplateService {


    /**
     * FreightTemplate条件+分页查询
     * @param freightTemplate 查询条件
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageResult<FreightTemplate> findPage(FreightTemplate freightTemplate, int page, int size){
         Page<FreightTemplate> mypage = new Page<>(page, size);
        QueryWrapper<FreightTemplate> queryWrapper = this.createQueryWrapper(freightTemplate);
        IPage<FreightTemplate> iPage = this.page(mypage, queryWrapper);
        return new PageResult<FreightTemplate>(iPage.getTotal(),iPage.getRecords());
    }

    /**
     * FreightTemplate分页查询
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageResult<FreightTemplate> findPage(int page, int size){
        Page<FreightTemplate> mypage = new Page<>(page, size);
        IPage<FreightTemplate> iPage = this.page(mypage, new QueryWrapper<FreightTemplate>());

        return new PageResult<FreightTemplate>(iPage.getTotal(),iPage.getRecords());
    }

    /**
     * FreightTemplate条件查询
     * @param freightTemplate
     * @return
     */
    @Override
    public List<FreightTemplate> findList(FreightTemplate freightTemplate){
        //构建查询条件
        QueryWrapper<FreightTemplate> queryWrapper = this.createQueryWrapper(freightTemplate);
        //根据构建的条件查询数据
        return this.list(queryWrapper);
    }


    /**
     * FreightTemplate构建查询对象
     * @param freightTemplate
     * @return
     */
    public QueryWrapper<FreightTemplate> createQueryWrapper(FreightTemplate freightTemplate){
        QueryWrapper<FreightTemplate> queryWrapper = new QueryWrapper<>();
        if(freightTemplate!=null){
            // 
            if(!StringUtils.isEmpty(freightTemplate.getId())){
                 queryWrapper.eq("id",freightTemplate.getId());
            }
            // 商家ID
            if(!StringUtils.isEmpty(freightTemplate.getSellerId())){
                 queryWrapper.eq("seller_id",freightTemplate.getSellerId());
            }
            // 是否默认   （‘Y’是   'N'否）
            if(!StringUtils.isEmpty(freightTemplate.getIsDefault())){
                 queryWrapper.eq("is_default",freightTemplate.getIsDefault());
            }
            // 模版名称
            if(!StringUtils.isEmpty(freightTemplate.getName())){
                queryWrapper.like("name",freightTemplate.getName());
            }
            // 发货时间（1:12h  2:24h  3:48h  4:72h  5:7d 6:15d ）
            if(!StringUtils.isEmpty(freightTemplate.getSendTimeType())){
                 queryWrapper.eq("send_time_type",freightTemplate.getSendTimeType());
            }
            // 统一价格
            if(!StringUtils.isEmpty(freightTemplate.getPrice())){
                 queryWrapper.eq("price",freightTemplate.getPrice());
            }
            // 创建时间
            if(!StringUtils.isEmpty(freightTemplate.getCreateTime())){
                 queryWrapper.eq("create_time",freightTemplate.getCreateTime());
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
     * 修改FreightTemplate
     * @param freightTemplate
     */
    @Override
    public void update(FreightTemplate freightTemplate){
        this.updateById(freightTemplate);
    }

    /**
     * 增加FreightTemplate
     * @param freightTemplate
     */
    @Override
    public void add(FreightTemplate freightTemplate){
        this.save(freightTemplate);
    }

    /**
     * 根据ID查询FreightTemplate
     * @param id
     * @return
     */
    @Override
    public FreightTemplate findById(Long id){
        return  this.getById(id);
    }

    /**
     * 查询FreightTemplate全部数据
     * @return
     */
    @Override
    public List<FreightTemplate> findAll() {
        return this.list(new QueryWrapper<FreightTemplate>());
    }
}
