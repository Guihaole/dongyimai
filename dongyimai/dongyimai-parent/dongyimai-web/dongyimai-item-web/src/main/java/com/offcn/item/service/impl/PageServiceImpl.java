package com.offcn.item.service.impl;

import com.alibaba.fastjson.JSON;
import com.offcn.entity.Result;
import com.offcn.item.service.PageService;
import com.offcn.sellergoods.entity.GoodsEntity;
import com.offcn.sellergoods.fegin.GoodsFeign;
import com.offcn.sellergoods.fegin.ItemCatFeign;
import com.offcn.sellergoods.pojo.Goods;
import com.offcn.sellergoods.pojo.GoodsDesc;
import com.offcn.sellergoods.pojo.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PageServiceImpl implements PageService {
    @Autowired
    private GoodsFeign goodsFeign;

    @Autowired
    private ItemCatFeign itemCatFeign;

    @Autowired
    private TemplateEngine templateEngine;

    //生成静态文件路径
    @Value("${pagepath}")
    private String pagepath;


    /**
     * 构建数据模型
     *
     * @param goodsId
     * @return
     */
    private Map<String, Object> buildDataModel(Long goodsId) {
        //构建数据模型
        Map<String, Object> dataMap = new HashMap<>();
        //获取SPU 和SKU列表
        Result<GoodsEntity> result = goodsFeign.findById(goodsId);
        GoodsEntity goodsEntity = result.getData();
        //1.加载SPU数据
        Goods goods = goodsEntity.getGoods();
        //2.加载商品扩展数据
        GoodsDesc goodsDesc = goodsEntity.getGoodsDesc();

        //3.加载SKU数据
        List<Item> itemList = goodsEntity.getItemList();

        dataMap.put("goods", goods);

        dataMap.put("goodsDesc", goodsDesc);
        //[
        // {"attributeName":"网络制式","attributeValue":["移动3G","移动4G"]},
        // {"attributeName":"屏幕尺寸","attributeValue":["6寸","5寸"]}
        // ]
        dataMap.put("specificationList", JSON.parseArray(goodsDesc.getSpecificationItems(),Map.class));
        //[
        // {"color":"红色","url":"http://192.168.25.133/group1/M00/00/01/wKgZhVmHINKADo__AAjlKdWCzvg874.jpg"},
        // {"color":"黑色","url":"http://192.168.25.133/group1/M00/00/01/wKgZhVmHINyAQAXHAAgawLS1G5Y136.jpg"}
        // ]
        dataMap.put("imageList",JSON.parseArray(goodsDesc.getItemImages(),Map.class));
        dataMap.put("itemList",itemList);

        //4.加载分类数据
        dataMap.put("category1",itemCatFeign.findById(goods.getCategory1Id()).getData());
        dataMap.put("category2",itemCatFeign.findById(goods.getCategory2Id()).getData());
        dataMap.put("category3",itemCatFeign.findById(goods.getCategory3Id()).getData());


        return dataMap;
    }

    /**
     * 根据商品的ID 生成静态页
     *
     * @param goodsId
     */
    @Override
    public boolean createPageHtml(Long goodsId) {
        // 1.上下文
        Context context = new Context();
        Map<String, Object> dataModel = buildDataModel(goodsId);
        context.setVariables(dataModel);
        // 2.准备文件
        File dir = new File(pagepath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File dest = new File(dir, goodsId + ".html");
        // 3.生成页面
        try (PrintWriter writer = new PrintWriter(dest, "UTF-8")) {
            templateEngine.process("item", context, writer);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
