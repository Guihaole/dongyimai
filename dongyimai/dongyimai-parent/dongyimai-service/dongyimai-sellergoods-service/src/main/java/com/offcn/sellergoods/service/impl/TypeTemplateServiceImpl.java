package com.offcn.sellergoods.service.impl;
import com.alibaba.fastjson.JSON;
import com.offcn.entity.Result;
import com.offcn.entity.StatusCode;
import com.offcn.sellergoods.dao.SpecificationOptionMapper;
import com.offcn.sellergoods.dao.TypeTemplateMapper;
import com.offcn.sellergoods.pojo.SpecificationOption;
import com.offcn.sellergoods.pojo.TypeTemplate;
import com.offcn.sellergoods.service.TypeTemplateService;
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
 * @Description:TypeTemplate业务层接口实现类
 * @Date 2021/2/1 14:19
 *****/
@Service
public class TypeTemplateServiceImpl extends ServiceImpl<TypeTemplateMapper,TypeTemplate> implements TypeTemplateService {


    @Autowired
    private TypeTemplateMapper typeTemplateMapper;

    @Autowired
    private SpecificationOptionMapper specificationOptionMapper;
    /**
     * TypeTemplate条件+分页查询
     * @param typeTemplate 查询条件
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageResult<TypeTemplate> findPage(TypeTemplate typeTemplate, int page, int size){
         Page<TypeTemplate> mypage = new Page<>(page, size);
        QueryWrapper<TypeTemplate> queryWrapper = this.createQueryWrapper(typeTemplate);
        IPage<TypeTemplate> iPage = this.page(mypage, queryWrapper);
        return new PageResult<TypeTemplate>(iPage.getTotal(),iPage.getRecords());
    }

    /**
     * TypeTemplate分页查询
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageResult<TypeTemplate> findPage(int page, int size){
        Page<TypeTemplate> mypage = new Page<>(page, size);
        IPage<TypeTemplate> iPage = this.page(mypage, new QueryWrapper<TypeTemplate>());

        return new PageResult<TypeTemplate>(iPage.getTotal(),iPage.getRecords());
    }

    /**
     * TypeTemplate条件查询
     * @param typeTemplate
     * @return
     */
    @Override
    public List<TypeTemplate> findList(TypeTemplate typeTemplate){
        //构建查询条件
        QueryWrapper<TypeTemplate> queryWrapper = this.createQueryWrapper(typeTemplate);
        //根据构建的条件查询数据
        return this.list(queryWrapper);
    }


    /**
     * TypeTemplate构建查询对象
     * @param typeTemplate
     * @return
     */
    public QueryWrapper<TypeTemplate> createQueryWrapper(TypeTemplate typeTemplate){
        QueryWrapper<TypeTemplate> queryWrapper = new QueryWrapper<>();
        if(typeTemplate!=null){
            //
            if(!StringUtils.isEmpty(typeTemplate.getId())){
                 queryWrapper.eq("id",typeTemplate.getId());
            }
            // 模板名称
            if(!StringUtils.isEmpty(typeTemplate.getName())){
                queryWrapper.like("name",typeTemplate.getName());
            }
            // 关联规格
            if(!StringUtils.isEmpty(typeTemplate.getSpecIds())){
                 queryWrapper.eq("spec_ids",typeTemplate.getSpecIds());
            }
            // 关联品牌
            if(!StringUtils.isEmpty(typeTemplate.getBrandIds())){
                 queryWrapper.eq("brand_ids",typeTemplate.getBrandIds());
            }
            // 自定义属性
            if(!StringUtils.isEmpty(typeTemplate.getCustomAttributeItems())){
                 queryWrapper.eq("custom_attribute_items",typeTemplate.getCustomAttributeItems());
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
     * 修改TypeTemplate
     * @param typeTemplate
     */
    @Override
    public void update(TypeTemplate typeTemplate){
        this.updateById(typeTemplate);
    }

    /**
     * 增加TypeTemplate
     * @param typeTemplate
     */
    @Override
    public void add(TypeTemplate typeTemplate){
        this.save(typeTemplate);
    }

    /**
     * 根据ID查询TypeTemplate
     * @param id
     * @return
     */
    @Override
    public Result<List<Map>> findById(Long id){
        TypeTemplate typeTemplate = typeTemplateMapper.selectById(id);
        String brandIds = typeTemplate.getBrandIds();
        List<Map> mapList = JSON.parseArray(brandIds, Map.class);
        return new Result<List<Map>>(true, StatusCode.OK,"查询品牌成功",mapList);
    }

    /**
     * 查询TypeTemplate全部数据
     * @return
     */
    @Override
    public List<TypeTemplate> findAll() {
        return this.list(new QueryWrapper<TypeTemplate>());
    }

    /**
     * 1.根据id查询字段spec_ids 转换mapList
     * 2. 遍历mapList 根据id查询 规格选项表 返回集合
     * 3. 将集合封装到map中
     * @param id
     * @return
     */
    @Override
    public Result<List<Map>> findSpecList(Long id) {
        TypeTemplate typeTemplate = typeTemplateMapper.selectById(id);
        String specIds = typeTemplate.getSpecIds();
        List<Map> mapList = JSON.parseArray(specIds, Map.class);
        if(mapList!=null&&mapList.size()>0){
            for (Map map : mapList) {
                Integer spec_id = (Integer)map.get("id");
                QueryWrapper<SpecificationOption> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("spec_id",Long.valueOf(spec_id));
                List<SpecificationOption> options = specificationOptionMapper.selectList(queryWrapper);
                map.put("options",options);
            }
        }
        return new Result<List<Map>>(true,StatusCode.OK,"查询成功",mapList);
    }
}
