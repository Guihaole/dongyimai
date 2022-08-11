package com.offcn.sellergoods.service.impl;

import com.offcn.entity.Result;
import com.offcn.entity.StatusCode;
import com.offcn.sellergoods.dao.ItemCatMapper;
import com.offcn.sellergoods.pojo.ItemCat;
import com.offcn.sellergoods.service.ItemCatService;
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
 * @Description:ItemCat业务层接口实现类
 * @Date 2021/2/1 14:19
 *****/
@Service
public class ItemCatServiceImpl extends ServiceImpl<ItemCatMapper, ItemCat> implements ItemCatService {

    @Autowired
    private ItemCatMapper itemCatMapper;

    /**
     * ItemCat条件+分页查询
     *
     * @param itemCat 查询条件
     * @param page    页码
     * @param size    页大小
     * @return 分页结果
     */
    @Override
    public PageResult<ItemCat> findPage(ItemCat itemCat, int page, int size) {
        Page<ItemCat> mypage = new Page<>(page, size);
        QueryWrapper<ItemCat> queryWrapper = this.createQueryWrapper(itemCat);
        IPage<ItemCat> iPage = this.page(mypage, queryWrapper);
        return new PageResult<ItemCat>(iPage.getTotal(), iPage.getRecords());
    }

    /**
     * ItemCat分页查询
     *
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageResult<ItemCat> findPage(int page, int size) {
        Page<ItemCat> mypage = new Page<>(page, size);
        IPage<ItemCat> iPage = this.page(mypage, new QueryWrapper<ItemCat>());

        return new PageResult<ItemCat>(iPage.getTotal(), iPage.getRecords());
    }

    /**
     * ItemCat条件查询
     *
     * @param itemCat
     * @return
     */
    @Override
    public List<ItemCat> findList(ItemCat itemCat) {
        //构建查询条件
        QueryWrapper<ItemCat> queryWrapper = this.createQueryWrapper(itemCat);
        //根据构建的条件查询数据
        return this.list(queryWrapper);
    }


    /**
     * ItemCat构建查询对象
     *
     * @param itemCat
     * @return
     */
    public QueryWrapper<ItemCat> createQueryWrapper(ItemCat itemCat) {
        QueryWrapper<ItemCat> queryWrapper = new QueryWrapper<>();
        if (itemCat != null) {
            // 类目ID
            if (!StringUtils.isEmpty(itemCat.getId())) {
                queryWrapper.eq("id", itemCat.getId());
            }
            // 父类目ID=0时，代表的是一级的类目
            if (!StringUtils.isEmpty(itemCat.getParentId())) {
                queryWrapper.eq("parent_id", itemCat.getParentId());
            }
            // 类目名称
            if (!StringUtils.isEmpty(itemCat.getName())) {
                queryWrapper.like("name", itemCat.getName());
            }
            // 类型id
            if (!StringUtils.isEmpty(itemCat.getTypeId())) {
                queryWrapper.eq("type_id", itemCat.getTypeId());
            }
        }
        return queryWrapper;
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public void delete(Long id) {
        this.removeById(id);
    }

    /**
     * 修改ItemCat
     *
     * @param itemCat
     */
    @Override
    public void update(ItemCat itemCat) {
        this.updateById(itemCat);
    }

    /**
     * 增加ItemCat
     *
     * @param itemCat
     */
    @Override
    public void add(ItemCat itemCat) {
        this.save(itemCat);
    }

    /**
     * 根据ID查询ItemCat
     *
     * @param id
     * @return
     */
    @Override
    public ItemCat findById(Long id) {
        return this.getById(id);
    }

    /**
     * 查询ItemCat全部数据
     *
     * @return
     */
    @Override
    public List<ItemCat> findAll() {
        return this.list(new QueryWrapper<ItemCat>());
    }

    /**
     * 根据parentId查询ItemCat集合
     *
     * @param parentId
     * @return
     */
    @Override
    public Result<List<ItemCat>> selectItemCat(Integer parentId) {
        QueryWrapper<ItemCat> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", parentId);
        List<ItemCat> itemCatList = itemCatMapper.selectList(queryWrapper);
        return new Result(true, StatusCode.OK, "查询成功", itemCatList);
    }

    /**
     * 递归查找分类列表
     *
     * @return
     */
    @Override
    public Result<List<ItemCat>> selectItemCatTree() {
        //1.查询一级分类列表
        QueryWrapper<ItemCat> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", 0);
        List<ItemCat> itemCatList = itemCatMapper.selectList(queryWrapper);
        //2.查询没给列表下的子分类
        if (itemCatList != null && itemCatList.size() > 0) {
            for (ItemCat itemCat : itemCatList) {
                getChildren(itemCat);
            }
        }
        return new Result<>(true, StatusCode.OK, "查询分类列表成功", itemCatList);
    }

    //递归方法
    private void getChildren(ItemCat itemCat) {
        QueryWrapper<ItemCat> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", itemCat.getId());
        List<ItemCat> itemCats = itemCatMapper.selectList(queryWrapper);
        itemCat.setChildren(itemCats);
        itemCat.setOpen(true);
        if (itemCats != null && itemCats.size() > 0) {
            for (ItemCat it : itemCats) {
                getChildren(it);
            }
        }
    }
}
