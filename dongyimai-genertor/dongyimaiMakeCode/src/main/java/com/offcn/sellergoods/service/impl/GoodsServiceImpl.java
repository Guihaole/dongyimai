package com.offcn.sellergoods.service.impl;
import com.offcn.sellergoods.dao.GoodsMapper;
import com.offcn.sellergoods.pojo.Goods;
import com.offcn.sellergoods.service.GoodsService;
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
 * @Description:Goods业务层接口实现类
 * @Date 2021/2/1 14:19
 *****/
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper,Goods> implements GoodsService {


    /**
     * Goods条件+分页查询
     * @param goods 查询条件
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageResult<Goods> findPage(Goods goods, int page, int size){
         Page<Goods> mypage = new Page<>(page, size);
        QueryWrapper<Goods> queryWrapper = this.createQueryWrapper(goods);
        IPage<Goods> iPage = this.page(mypage, queryWrapper);
        return new PageResult<Goods>(iPage.getTotal(),iPage.getRecords());
    }

    /**
     * Goods分页查询
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageResult<Goods> findPage(int page, int size){
        Page<Goods> mypage = new Page<>(page, size);
        IPage<Goods> iPage = this.page(mypage, new QueryWrapper<Goods>());

        return new PageResult<Goods>(iPage.getTotal(),iPage.getRecords());
    }

    /**
     * Goods条件查询
     * @param goods
     * @return
     */
    @Override
    public List<Goods> findList(Goods goods){
        //构建查询条件
        QueryWrapper<Goods> queryWrapper = this.createQueryWrapper(goods);
        //根据构建的条件查询数据
        return this.list(queryWrapper);
    }


    /**
     * Goods构建查询对象
     * @param goods
     * @return
     */
    public QueryWrapper<Goods> createQueryWrapper(Goods goods){
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        if(goods!=null){
            // 主键
            if(!StringUtils.isEmpty(goods.getId())){
                 queryWrapper.eq("id",goods.getId());
            }
            // 商家ID
            if(!StringUtils.isEmpty(goods.getSellerId())){
                 queryWrapper.eq("seller_id",goods.getSellerId());
            }
            // SPU名
            if(!StringUtils.isEmpty(goods.getGoodsName())){
                 queryWrapper.eq("goods_name",goods.getGoodsName());
            }
            // 默认SKU
            if(!StringUtils.isEmpty(goods.getDefaultItemId())){
                 queryWrapper.eq("default_item_id",goods.getDefaultItemId());
            }
            // 状态
            if(!StringUtils.isEmpty(goods.getAuditStatus())){
                 queryWrapper.eq("audit_status",goods.getAuditStatus());
            }
            // 是否上架
            if(!StringUtils.isEmpty(goods.getIsMarketable())){
                 queryWrapper.eq("is_marketable",goods.getIsMarketable());
            }
            // 品牌
            if(!StringUtils.isEmpty(goods.getBrandId())){
                 queryWrapper.eq("brand_id",goods.getBrandId());
            }
            // 副标题
            if(!StringUtils.isEmpty(goods.getCaption())){
                 queryWrapper.eq("caption",goods.getCaption());
            }
            // 一级类目
            if(!StringUtils.isEmpty(goods.getCategory1Id())){
                 queryWrapper.eq("category1_id",goods.getCategory1Id());
            }
            // 二级类目
            if(!StringUtils.isEmpty(goods.getCategory2Id())){
                 queryWrapper.eq("category2_id",goods.getCategory2Id());
            }
            // 三级类目
            if(!StringUtils.isEmpty(goods.getCategory3Id())){
                 queryWrapper.eq("category3_id",goods.getCategory3Id());
            }
            // 小图
            if(!StringUtils.isEmpty(goods.getSmallPic())){
                 queryWrapper.eq("small_pic",goods.getSmallPic());
            }
            // 商城价
            if(!StringUtils.isEmpty(goods.getPrice())){
                 queryWrapper.eq("price",goods.getPrice());
            }
            // 分类模板ID
            if(!StringUtils.isEmpty(goods.getTypeTemplateId())){
                 queryWrapper.eq("type_template_id",goods.getTypeTemplateId());
            }
            // 是否启用规格
            if(!StringUtils.isEmpty(goods.getIsEnableSpec())){
                 queryWrapper.eq("is_enable_spec",goods.getIsEnableSpec());
            }
            // 是否删除
            if(!StringUtils.isEmpty(goods.getIsDelete())){
                 queryWrapper.eq("is_delete",goods.getIsDelete());
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
     * 修改Goods
     * @param goods
     */
    @Override
    public void update(Goods goods){
        this.updateById(goods);
    }

    /**
     * 增加Goods
     * @param goods
     */
    @Override
    public void add(Goods goods){
        this.save(goods);
    }

    /**
     * 根据ID查询Goods
     * @param id
     * @return
     */
    @Override
    public Goods findById(Long id){
        return  this.getById(id);
    }

    /**
     * 查询Goods全部数据
     * @return
     */
    @Override
    public List<Goods> findAll() {
        return this.list(new QueryWrapper<Goods>());
    }
}
