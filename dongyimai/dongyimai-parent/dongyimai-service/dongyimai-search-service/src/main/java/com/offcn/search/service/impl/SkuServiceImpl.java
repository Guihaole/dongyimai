package com.offcn.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.offcn.entity.Result;
import com.offcn.search.dao.SkuEsMapper;
import com.offcn.search.pojo.SkuInfo;
import com.offcn.search.service.SkuService;
import com.offcn.sellergoods.fegin.ItemFeign;
import com.offcn.sellergoods.pojo.Brand;
import com.offcn.sellergoods.pojo.Item;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.*;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.*;

@Service
public class SkuServiceImpl implements SkuService {
    @Autowired
    private ItemFeign itemFeign;
    @Autowired
    private SkuEsMapper skuEsMapper;
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    /**
     * 逻辑：
     * 1.接收itemFeign传输过来的数据Result
     * 2. 将数据存入搜索引擎es
     */
    @Override
    public void importSku() {
        Result<List<Item>> result = itemFeign.findByStatus("1");
        List<Item> itemList = result.getData();
        //将获取的itemList表单项转换为我们es存储的类型
        List<SkuInfo> skuInfoList = JSON.parseArray(JSON.toJSONString(itemList), SkuInfo.class);
        for (SkuInfo skuInfo : skuInfoList) {
            String spec = skuInfo.getSpec();
            skuInfo.setSpecMap(JSON.parseObject(spec));
        }
        skuEsMapper.saveAll(skuInfoList);
        //插入的spec可以直接转哦
//        Optional<SkuInfo> byId = skuEsMapper.findById(1l);
//        SkuInfo skuInfo = byId.get();
//        Map map = JSON.parseObject(skuInfo.getSpec(), Map.class);
    }

    /**
     * 需求：搜索
     * 逻辑：根据字段全文检索
     * 1. 这个只是查询了我们页面相关的检索数据
     *   1.1 查询相关数据的分类信息显示页面
     * @return
     */
    @Override
    public Map searchSku(Map searchMap) {
        //1.获取关键字的值
        String keywords = (String)searchMap.get("keywords");
        if (keywords.isEmpty()) {
            keywords="华为";
        }
        //2.创建查询对象 的构建对象
        NativeSearchQueryBuilder nativeSearchQueryBuilder =
                new NativeSearchQueryBuilder();
        //此处都是查询条件
        //1.1  设置分类分组条件
        nativeSearchQueryBuilder.addAggregation(AggregationBuilders
                .terms("skuCategorygroup")
                .field("category")
                .size(50));
        //2.1 设置品牌分组条件
        nativeSearchQueryBuilder.addAggregation(AggregationBuilders
                .terms("skuBrandgroup")
                .field("brand")
                .size(50));
        //3.1 设置规格分组条件
        nativeSearchQueryBuilder.addAggregation(AggregationBuilders
                .terms("skuSpecgroup")
                .field("spec.keyword")
                .size(100));
        //9.1 高亮字段设置
        nativeSearchQueryBuilder.withHighlightFields(new HighlightBuilder.Field("title"));
        nativeSearchQueryBuilder.withHighlightBuilder(new HighlightBuilder()
                .preTags("<em style=\"color:red\">").postTags("</em>"));
        nativeSearchQueryBuilder.withQuery(QueryBuilders
                .multiMatchQuery(keywords,"title","brand","category"));
        //3.构建查询条件
        //nativeSearchQueryBuilder.withQuery(QueryBuilders.matchQuery("title",keywords));
        //4.1 构建过滤条件  查询匹配中并由这些关键字的
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (searchMap!=null&&searchMap.containsKey("brand")) {
            if (!StringUtils.isEmpty(String.valueOf(searchMap.get("brand")))) {
                boolQueryBuilder.filter(QueryBuilders.termQuery("brand",searchMap.get("brand")));
            }
        }
        if (searchMap!=null&&searchMap.containsKey("category")) {
            if (!StringUtils.isEmpty(String.valueOf(searchMap.get("category")))) {
                boolQueryBuilder.filter(QueryBuilders.termQuery("category",searchMap.get("category")));
            }
        }
        //5.1 构建过滤条件，查询匹配中并有规格关键字
        if (searchMap!=null) {
            for (Object key : searchMap.keySet()) {
                String keyStr = String.valueOf(key);
                if (keyStr.startsWith("spec_")) {
                    boolQueryBuilder.filter(QueryBuilders
                            .termQuery("specMap."+keyStr.substring(5)+".keyword"
                                    ,searchMap.get(keyStr)));
                }
            }
        }
        //6.1 构建过滤条件，价格过滤查询
        if(!searchMap.isEmpty()&&searchMap.containsKey("price")){
            String price = (String) searchMap.get("price");
            if (!StringUtils.isEmpty(price)) {
                String[] split = price.split("-");
                if (!split[1].equalsIgnoreCase("*")){
                    boolQueryBuilder.filter(QueryBuilders.rangeQuery("price")
                    .from(split[0],true).to(split[1],true));
                }else {
                    boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").gte(split[0]));
                }
            }
        }
        nativeSearchQueryBuilder.withFilter(boolQueryBuilder);
        //7.1 构建分页条件
        Integer pageNum=1;
        if (searchMap!=null&&searchMap.containsKey("pageNum")) {
            if (!StringUtils.isEmpty(String.valueOf(searchMap.get("pageNum")))) {
                try {
                    pageNum=Integer.valueOf((String) searchMap.get("pageNum"));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    pageNum=1;
                }
            }
            Integer pageSize=3;
            nativeSearchQueryBuilder.withPageable(PageRequest.of(pageNum-1,pageSize));
        }
        //8.1 构建排序根据价格降序排列
        if (searchMap!=null&&searchMap.containsKey("sortField")) {
            //拥有排序字段
            String sortField = (String)searchMap.get("sortField");
            String  sortRule=null;
            if(!searchMap.containsKey("sortRule")){
                sortRule="DESC";
            }else {
                sortRule =(String) searchMap.get("sortRule");
            }
            if (!StringUtils.isEmpty(sortField)&&!StringUtils.isEmpty(sortRule)) {
                nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort(sortField)
                .order(sortRule.equals("DESC")? SortOrder.DESC:SortOrder.ASC));
            }
        }
        //4.构建查询对象
        NativeSearchQuery searchQuery = nativeSearchQueryBuilder.build();
        //5. 执行搜索，获取封装响应数据结果的SearchHits集合
        SearchHits<SkuInfo> searchHits = elasticsearchRestTemplate.search(searchQuery, SkuInfo.class);
        //1.2 获取分类分组结果
        Terms categoryGroupTerms = searchHits.getAggregations().get("skuCategorygroup");
        List<String> categoryList = getStringsCategoryList(categoryGroupTerms);
        //2.2 获取品牌分组结果
        Terms skuBrandGroupTerms = searchHits.getAggregations().get("skuBrandgroup");
        List<String> brandList = getStringsBrandList(skuBrandGroupTerms);
        //3.2 获取规格分组结果
        Terms skuSpecgroupTerms = searchHits.getAggregations().get("skuSpecgroup");
        Map<String, Set<String>> skuSpecMap = getSkuSpecMap(skuSpecgroupTerms);
        //6.将查询结果对其进行分页封装
        SearchPage<SkuInfo> searchPage = SearchHitSupport.searchPageFor(searchHits, searchQuery.getPageable());
        //加入
        ArrayList<SkuInfo> skuInfoList = new ArrayList<>();
        for (SearchHit<SkuInfo> searchHit : searchPage.getContent()) {
            SkuInfo content = searchHit.getContent();
            SkuInfo skuInfo = new SkuInfo();
            BeanUtils.copyProperties(content,skuInfo);
            //处理高亮
            Map<String, List<String>> highlightFields = searchHit.getHighlightFields();
            for (Map.Entry<String, List<String>> stringListEntry : highlightFields.entrySet()) {
                String key = stringListEntry.getKey();
                if (StringUtils.equals(key,"title")) {
                    List<String> fragments = stringListEntry.getValue();
                    StringBuilder sb = new StringBuilder();
                    for (String fragment : fragments) {
                        sb.append(fragment.toString());
                    }
                    skuInfo.setTitle(sb.toString());
                }
            }
            skuInfoList.add(skuInfo);
        }
        //7.返回结果
        HashMap<String, Object> map = new HashMap<>();
        map.put("categoryList", categoryList);
        map.put("brandList", brandList);
        map.put("skuSpecMap",skuSpecMap);
        //map.put("rows", searchPage.getContent());//获取所需SkuInfo集合数据内容
        map.put("rows", skuInfoList);//获取所需高亮集合数据内容
        map.put("total", searchPage.getTotalElements());//总记录数
        map.put("totalPages", searchPage.getTotalPages());//总页数
        //分页数据保存
        //设置当前页码
        map.put("pageNum", pageNum);
        map.put("pageSize", 10);
        return map;
    }

    /**
     * 将字符串
     * {"机身内存":"32G","网络":"联通4G"}
     * {"机身内存":"64G","网络":"电信4G"}
     * {"机身内存":"64G","网络":"移动4G"}
     * 转换成====>
     * {
     *     "机身内存":["32G","64G","64G"],
     *     "网络":["联通4G","电信4G","移动4G"]
     * }
     * @param skuSpecgroupTerms
     * @return
     */
    private Map<String, Set<String>> getSkuSpecMap(Terms skuSpecgroupTerms) {
        Map<String, Set<String>> setMap = new HashMap<>();
        for (Terms.Bucket bucket : skuSpecgroupTerms.getBuckets()) {
            //1.{"机身内存"="32G","网络"="联通4G"}
            Map<String,String> map = JSON.parseObject(bucket.getKeyAsString(), Map.class);
            //2.遍历map集合
            Set<String> keySet = map.keySet();
            for (String key : keySet) {
                //3.如果没有这个key 创建set集合
                if(!setMap.containsKey(key)){
                    HashSet<String> set = new HashSet<>();
                    setMap.put(key,set);
                }
                //4.如果有这个key 直接添加
                String value = map.get(key);
                setMap.get(key).add(value);
            }
        }
        return setMap;
    }
    /**
     * 获取规格列表数据
     * 笔记上的，没有用到
     * @param termsSpec
     * @return
     */
    // Set去重
    private Map<String, Set<String>> getStringSetMap(Terms termsSpec) {
        Map<String, Set<String>> specMap = new HashMap<String, Set<String>>();
        Set<String> specList = new HashSet<>();
        if (termsSpec != null) {
            for (Terms.Bucket bucket : termsSpec.getBuckets()) {
                specList.add(bucket.getKeyAsString());
            }
        }
        for (String specjson : specList) {
            Map<String, String> map = JSON.parseObject(specjson, Map.class);
            for (Map.Entry<String, String> entry : map.entrySet()) {//
                String key = entry.getKey();        //规格名字
                String value = entry.getValue();    //规格选项值
                //获取当前规格名字对应的规格数据
                Set<String> specValues = specMap.get(key);
                if (specValues == null) {
                    specValues = new HashSet<String>();
                }
                //将当前规格加入到集合中
                specValues.add(value);
                //将数据存入到specMap中
                specMap.put(key, specValues);
            }
        }
        return specMap;
    }
    /**
     * 获取品牌列表数据
     * @param skuBrandGroupTerms
     * @return
     */
    private List<String> getStringsBrandList(Terms skuBrandGroupTerms) {
        ArrayList<String> brandList = new ArrayList<>();
        for (Terms.Bucket bucket : skuBrandGroupTerms.getBuckets()) {
            String keyAsString = bucket.getKeyAsString();
            brandList.add(keyAsString);
        }
        return brandList;
    }

    /**
     * 获取分类列表数据
     *
     * @param categoryGroupTerms
     * @return
     */
    private List<String> getStringsCategoryList(Terms categoryGroupTerms) {
        List<String> categoryList = new ArrayList<>();
        if (categoryGroupTerms != null) {
            for (Terms.Bucket bucket : categoryGroupTerms.getBuckets()) {
                String keyAsString = bucket.getKeyAsString();//分组的值
                categoryList.add(keyAsString);
            }
        }
        return categoryList;
    }
}
