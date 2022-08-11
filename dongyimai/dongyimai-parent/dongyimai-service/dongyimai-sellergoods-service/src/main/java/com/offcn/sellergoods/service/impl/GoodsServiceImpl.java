package com.offcn.sellergoods.service.impl;

import com.alibaba.fastjson.JSON;
import com.offcn.entity.Result;
import com.offcn.entity.StatusCode;
import com.offcn.sellergoods.dao.*;
import com.offcn.sellergoods.entity.GoodsEntity;
import com.offcn.sellergoods.pojo.*;
import com.offcn.sellergoods.service.GoodsService;
import com.offcn.entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/****
 * @Author:ujiuye
 * @Description:Goods业务层接口实现类
 * @Date 2021/2/1 14:19
 *****/
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {


    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private GoodsDescMapper goodsDescMapper;
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private ItemCatMapper itemCatMapper;
    @Autowired
    private BrandMapper brandMapper;

    /**
     * Goods条件+分页查询
     *
     * @param goods 查询条件
     * @param page  页码
     * @param size  页大小
     * @return 分页结果
     */
    @Override
    public PageResult<Goods> findPage(Goods goods, int page, int size) {
        Page<Goods> mypage = new Page<>(page, size);
        QueryWrapper<Goods> queryWrapper = this.createQueryWrapper(goods);
        IPage<Goods> iPage = this.page(mypage, queryWrapper);
        return new PageResult<Goods>(iPage.getTotal(), iPage.getRecords());
    }

    /**
     * Goods分页查询
     *
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageResult<Goods> findPage(int page, int size) {
        Page<Goods> mypage = new Page<>(page, size);
        IPage<Goods> iPage = this.page(mypage, new QueryWrapper<Goods>());

        return new PageResult<Goods>(iPage.getTotal(), iPage.getRecords());
    }

    /**
     * Goods条件查询
     *
     * @param goods
     * @return
     */
    @Override
    public List<Goods> findList(Goods goods) {
        //构建查询条件
        QueryWrapper<Goods> queryWrapper = this.createQueryWrapper(goods);
        //根据构建的条件查询数据
        return this.list(queryWrapper);
    }


    /**
     * Goods构建查询对象
     *
     * @param goods
     * @return
     */
    public QueryWrapper<Goods> createQueryWrapper(Goods goods) {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        if (goods != null) {
            // 主键
            if (!StringUtils.isEmpty(goods.getId())) {
                queryWrapper.eq("id", goods.getId());
            }
            // 商家ID
            if (!StringUtils.isEmpty(goods.getSellerId())) {
                queryWrapper.eq("seller_id", goods.getSellerId());
            }
            // SPU名
            if (!StringUtils.isEmpty(goods.getGoodsName())) {
                queryWrapper.eq("goods_name", goods.getGoodsName());
            }
            // 默认SKU
            if (!StringUtils.isEmpty(goods.getDefaultItemId())) {
                queryWrapper.eq("default_item_id", goods.getDefaultItemId());
            }
            // 状态
            if (!StringUtils.isEmpty(goods.getAuditStatus())) {
                queryWrapper.eq("audit_status", goods.getAuditStatus());
            }
            // 是否上架
            if (!StringUtils.isEmpty(goods.getIsMarketable())) {
                queryWrapper.eq("is_marketable", goods.getIsMarketable());
            }
            // 品牌
            if (!StringUtils.isEmpty(goods.getBrandId())) {
                queryWrapper.eq("brand_id", goods.getBrandId());
            }
            // 副标题
            if (!StringUtils.isEmpty(goods.getCaption())) {
                queryWrapper.eq("caption", goods.getCaption());
            }
            // 一级类目
            if (!StringUtils.isEmpty(goods.getCategory1Id())) {
                queryWrapper.eq("category1_id", goods.getCategory1Id());
            }
            // 二级类目
            if (!StringUtils.isEmpty(goods.getCategory2Id())) {
                queryWrapper.eq("category2_id", goods.getCategory2Id());
            }
            // 三级类目
            if (!StringUtils.isEmpty(goods.getCategory3Id())) {
                queryWrapper.eq("category3_id", goods.getCategory3Id());
            }
            // 小图
            if (!StringUtils.isEmpty(goods.getSmallPic())) {
                queryWrapper.eq("small_pic", goods.getSmallPic());
            }
            // 商城价
            if (!StringUtils.isEmpty(goods.getPrice())) {
                queryWrapper.eq("price", goods.getPrice());
            }
            // 分类模板ID
            if (!StringUtils.isEmpty(goods.getTypeTemplateId())) {
                queryWrapper.eq("type_template_id", goods.getTypeTemplateId());
            }
            // 是否启用规格
            if (!StringUtils.isEmpty(goods.getIsEnableSpec())) {
                queryWrapper.eq("is_enable_spec", goods.getIsEnableSpec());
            }
            // 是否删除
            if (!StringUtils.isEmpty(goods.getIsDelete())) {
                queryWrapper.eq("is_delete", goods.getIsDelete());
            }
        }
        return queryWrapper;
    }

    /**
     * 删除
     * 逻辑：
     * 1. 检查商品是否下架
     * 2.根据id 修改删除字段为1
     * 3. 并修改字段 审核为0
     *
     * @param id
     */
    @Override
    public Result<Goods> delete(Long id) {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        Goods goods = goodsMapper.selectOne(queryWrapper);
        if(goods==null){
            return new Result<Goods>(false,StatusCode.ERROR,"没有该商品");
        }
        if ("1".equals(goods.getIsMarketable())) {
            return new Result<Goods>(false,StatusCode.ERROR,"该商品在上架，不能删除");
        }
        //删除商品
        goods.setAuditStatus("0");
        goods.setIsDelete("1");
        goodsMapper.updateById(goods);
        return new Result<Goods>(true,StatusCode.OK,"商品删除成功");
    }

    /**
     * 修改Goods
     *
     * @param goodsEntity 1. 根据前端传过来的goods更新 goods表中的数据  主键修改
     *                    2. 根据前端传过来的good_desc信息更新 good_desc表的数据 主键修改
     *                    3. 根据goods_id删除原来的的数据
     *                    4. 调用添加的逻辑
     */
    @Override
    public void update(GoodsEntity goodsEntity) {
        //将审核状态重新设置为 未审核
        goodsEntity.getGoods().setAuditStatus("0");
        goodsMapper.updateById(goodsEntity.getGoods());
        goodsDescMapper.updateById(goodsEntity.getGoodsDesc());
        QueryWrapper<Item> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("goods_id", goodsEntity.getGoods().getId());
        itemMapper.delete(queryWrapper);
        saveItemList(goodsEntity);
    }

    /**
     * 增加Goods
     *
     * @param goodsEntity 逻辑：
     *                    1. 根据前端传来的 goods数据 添加商品表,返回商品id
     *                    2. 根据商品id向goods_desc表里添加记录
     *                    3. 增加 item表单项
     */
    @Transactional
    @Override
    public void add(GoodsEntity goodsEntity) {
        //将审核状态重新设置为 未审核
        goodsEntity.getGoods().setAuditStatus("0");
        goodsMapper.insert(goodsEntity.getGoods());
        goodsEntity.getGoodsDesc().setGoodsId(goodsEntity.getGoods().getId());
        goodsDescMapper.insert(goodsEntity.getGoodsDesc());
        saveItemList(goodsEntity);
    }


    //修改和保存的公用逻辑
    private void saveItemList(GoodsEntity goodsEntity) {
        //新增sku表
        List<Item> itemList = goodsEntity.getItemList();
        if ("1".equals(goodsEntity.getGoods().getIsEnableSpec())) {
            if (itemList != null && itemList.size() > 0) {
                for (Item item : itemList) {
                    //1.为title添加参数设置
                    String title = goodsEntity.getGoods().getGoodsName();
                    String spec = item.getSpec();
                    Map<String, String> specMap = JSON.parseObject(spec, Map.class);
                    for (String key : specMap.keySet()) {
                        title += " " + specMap.get(key);
                    }
                    item.setTitle(title);
                    this.setItemValue(goodsEntity, item);
                    itemMapper.insert(item);
                }
            }
        } else {
            //不启用规格  SKU信息为默认值
            Item item = new Item();
            item.setTitle(goodsEntity.getGoods().getGoodsName());     //商品名称
            item.setPrice(goodsEntity.getGoods().getPrice());      //默认使用SPU的价格
            item.setNum(9999);
            item.setStatus("1");            //是否启用
            item.setIsDefault("1");         //是否默认
            item.setSpec("{}");             //没有选择规格，则放置空JSON结构
            this.setItemValue(goodsEntity, item);
            itemMapper.insert(item);
        }
    }

    private void setItemValue(GoodsEntity goodsEntity, Item item) {
        item.setCategoryId(goodsEntity.getGoods().getCategory3Id());
        item.setCreateTime(new Date());
        item.setUpdateTime(new Date());
        item.setGoodsId(goodsEntity.getGoods().getId());
        item.setSellerId(goodsEntity.getGoods().getSellerId());
        ItemCat itemCat = itemCatMapper.selectById(goodsEntity.getGoods().getCategory3Id());
        item.setCategory(itemCat.getName());
        Brand brand = brandMapper.selectById(goodsEntity.getGoods().getBrandId());
        item.setBrand(brand.getName());
        String itemImages = goodsEntity.getGoodsDesc().getItemImages();
        List<Map> mapList = JSON.parseArray(itemImages, Map.class);
        if (mapList != null && mapList.size() > 0) {
            item.setImage((String) mapList.get(0).get("url"));
        }
    }

    /**
     * 根据ID查询Goods
     *
     * @param id
     * @return 逻辑：1.根据goods表的id查询goods信息
     * 2. 根据id查询 goods_desc表信息
     * 3. 根据goods_id查询item表返回集合
     */
    @Override
    public GoodsEntity findById(Long id) {
        Goods goods = goodsMapper.selectById(id);
        GoodsDesc goodsDesc = goodsDescMapper.selectById(id);
        QueryWrapper<Item> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("goods_id", id);
        List<Item> itemList = itemMapper.selectList(queryWrapper);
        GoodsEntity goodsEntity = new GoodsEntity();
        goodsEntity.setGoods(goods);
        goodsEntity.setGoodsDesc(goodsDesc);
        goodsEntity.setItemList(itemList);
        return goodsEntity;
    }

    /**
     * 查询Goods全部数据
     *
     * @return
     */
    @Override
    public List<Goods> findAll() {
        return this.list(new QueryWrapper<Goods>());
    }

    /**
     * 根据id进行查询，判断是否删除，
     * 如果未删除则AuditStatus审核设置为1审核通过，然后IsMarketable设置为1上架)
     *
     * @param id
     */
    @Override
    public Result audit(Long id) {
        Goods goods = goodsMapper.selectById(id);
        if (goods.getIsDelete() != null && "1".equals(goods.getIsDelete())) {
            return new Result(false, StatusCode.ERROR, "审核未通过，该商品已经被删除");
        }
        //修改
        goods.setAuditStatus("1");
        goods.setIsMarketable("1");
        goodsMapper.updateById(goods);
        return new Result(true, StatusCode.OK, "审核通过，商品已上架");
    }

    /**
     * 商品下架逻辑：
     * 1. 判断商品是否删除，如果未删除下架商品
     *
     * @param id
     * @return
     */
    @Override
    public Result pull(Long id) {
        Goods goods = goodsMapper.selectById(id);
        if (goods.getIsDelete() != null && "1".equals(goods.getIsDelete())) {
            return new Result(false, StatusCode.ERROR, "商品下架失败，商品已经删除");
        }
        goods.setIsMarketable("0");
        goodsMapper.updateById(goods);
        return new Result(true, StatusCode.OK, "商品已下架");
    }

    /**
     * 逻辑：
     * 1.判断商品是否删除，删除直接返回
     * 2.商品未删除,查看商品的审核是否通过 未通过直接返回
     * 3.审核通过，修改商品状态
     *
     * @param id
     * @return
     */
    @Override
    public Result put(Long id) {
        Goods goods = goodsMapper.selectById(id);
        if (goods.getIsDelete() != null && "1".equals(goods.getIsDelete())) {
            return new Result(false, StatusCode.ERROR, "商品上架失败，商品已经删除");
        }
        if (goods.getAuditStatus() != null && !"1".equals(goods.getAuditStatus())) {
            return new Result(false, StatusCode.ERROR, "商品未通过审核，不可上架");
        }
        goods.setIsMarketable("1");
        goodsMapper.updateById(goods);
        return new Result(true, StatusCode.OK, "商品上架成功");
    }

    /**
     * 批量上架：
     * 逻辑：
     * 1. 先判断是否删除
     * 2. 在判断是否通过审核
     * 3. 在进行批量修改
     *
     * @param ids
     * @return
     */
    @Override
    public Result putMany(Long[] ids) {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper
                //要修改的
                .in("id", Arrays.asList(ids))
                //未删除的
                .eq("is_delete", 0)
                .or()
                .isNull("is_delete")
                //通过审核的
                .eq("audit_status", 1)
                //下架的
                .eq("is_marketable", 0);
        Goods goods = new Goods();
        goods.setIsMarketable("1");
        goodsMapper.update(goods, queryWrapper);
        return new Result(true, StatusCode.OK, "批量上架成功");
    }

    /**
     * 商品下架逻辑:
     * 1. 查找批量的
     * 2. 查找未删除的
     * 3. 上架的
     * 4. 批量下架
     *
     * @param ids
     * @return
     */
    @Override
    public Result pullMany(Long[] ids) {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper
                //未删除的
                .eq("is_delete", 0)
                .or()
                .isNull("is_delete")
                //要修改的
                .in("id", Arrays.asList(ids))
                //上架的
                .eq("is_marketable", 1);
        Goods goods = new Goods();
        goods.setIsMarketable("0");
        goodsMapper.update(goods, queryWrapper);
        return new Result(true, StatusCode.OK, "批量下架成功");
    }
}
