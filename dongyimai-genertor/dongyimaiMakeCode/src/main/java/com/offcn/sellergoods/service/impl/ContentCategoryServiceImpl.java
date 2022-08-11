package com.offcn.sellergoods.service.impl;
import com.offcn.sellergoods.dao.ContentCategoryMapper;
import com.offcn.sellergoods.pojo.ContentCategory;
import com.offcn.sellergoods.service.ContentCategoryService;
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
 * @Description:ContentCategory业务层接口实现类
 * @Date 2021/2/1 14:19
 *****/
@Service
public class ContentCategoryServiceImpl extends ServiceImpl<ContentCategoryMapper,ContentCategory> implements ContentCategoryService {


    /**
     * ContentCategory条件+分页查询
     * @param contentCategory 查询条件
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageResult<ContentCategory> findPage(ContentCategory contentCategory, int page, int size){
         Page<ContentCategory> mypage = new Page<>(page, size);
        QueryWrapper<ContentCategory> queryWrapper = this.createQueryWrapper(contentCategory);
        IPage<ContentCategory> iPage = this.page(mypage, queryWrapper);
        return new PageResult<ContentCategory>(iPage.getTotal(),iPage.getRecords());
    }

    /**
     * ContentCategory分页查询
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageResult<ContentCategory> findPage(int page, int size){
        Page<ContentCategory> mypage = new Page<>(page, size);
        IPage<ContentCategory> iPage = this.page(mypage, new QueryWrapper<ContentCategory>());

        return new PageResult<ContentCategory>(iPage.getTotal(),iPage.getRecords());
    }

    /**
     * ContentCategory条件查询
     * @param contentCategory
     * @return
     */
    @Override
    public List<ContentCategory> findList(ContentCategory contentCategory){
        //构建查询条件
        QueryWrapper<ContentCategory> queryWrapper = this.createQueryWrapper(contentCategory);
        //根据构建的条件查询数据
        return this.list(queryWrapper);
    }


    /**
     * ContentCategory构建查询对象
     * @param contentCategory
     * @return
     */
    public QueryWrapper<ContentCategory> createQueryWrapper(ContentCategory contentCategory){
        QueryWrapper<ContentCategory> queryWrapper = new QueryWrapper<>();
        if(contentCategory!=null){
            // 类目ID
            if(!StringUtils.isEmpty(contentCategory.getId())){
                 queryWrapper.eq("id",contentCategory.getId());
            }
            // 分类名称
            if(!StringUtils.isEmpty(contentCategory.getName())){
                queryWrapper.like("name",contentCategory.getName());
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
     * 修改ContentCategory
     * @param contentCategory
     */
    @Override
    public void update(ContentCategory contentCategory){
        this.updateById(contentCategory);
    }

    /**
     * 增加ContentCategory
     * @param contentCategory
     */
    @Override
    public void add(ContentCategory contentCategory){
        this.save(contentCategory);
    }

    /**
     * 根据ID查询ContentCategory
     * @param id
     * @return
     */
    @Override
    public ContentCategory findById(Long id){
        return  this.getById(id);
    }

    /**
     * 查询ContentCategory全部数据
     * @return
     */
    @Override
    public List<ContentCategory> findAll() {
        return this.list(new QueryWrapper<ContentCategory>());
    }
}
