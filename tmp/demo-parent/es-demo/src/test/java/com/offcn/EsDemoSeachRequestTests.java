package com.offcn;

import com.alibaba.fastjson.JSON;
import com.offcn.pojo.Goods;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;

@SpringBootTest
public class EsDemoSeachRequestTests {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 查询所有
     * 1. matchAll
     * 2. 将查询结果封装为Goods对象，装载到List中
     * 3. 分页。默认显示10条
     */
    @Test
    public void matchAll() throws IOException {
        //2. 构建查询请求对象，指定查询的索引名称
        SearchRequest searchRequest = new SearchRequest("goods");
        //4. 创建查询条件构建器SearchSourceBuilder
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //6. 查询条件matchAllQuery
//==============================查询条件matchAllQuery=============================================
//       MatchAllQueryBuilder queryBuilder = QueryBuilders.matchAllQuery();
//==============================查询华为或手机的相关数据 match=============================================
//        MatchQueryBuilder queryBuilder = QueryBuilders.matchQuery("title", "华为手机").operator(Operator.AND);
//==============================模糊查询wildcardQuery=============================================
//        WildcardQueryBuilder queryBuilder = QueryBuilders.wildcardQuery("title", "华*");
//        RangeQueryBuilder queryBuilder = QueryBuilders.rangeQuery("price")
//                .gte(2000).lte(3000);
//        sourceBuilder.sort("price", SortOrder.DESC);
//==============================多字段匹配查询=============================================
        QueryStringQueryBuilder queryBuilder = QueryBuilders.queryStringQuery("华为手机")
                .field("title").field("categoryName")
                .field("brandName").defaultOperator(Operator.AND);
        //5. 指定查询条件
        //8 . 添加分页信息  不设置 默认10条
        sourceBuilder.query(queryBuilder);
        //sourceBuilder.from(0);
        //sourceBuilder.size(20);
        //3. 添加查询条件构建器 SearchSourceBuilder
        searchRequest.source(sourceBuilder);
        //1. 查询,获取查询结果
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        //7.得到结果
        SearchHits hits = searchResponse.getHits();
        Long total = hits.getTotalHits().value;
        System.out.println("总数："+total);
        SearchHit[] hits1 = hits.getHits();
        ArrayList<Goods> goodsList = new ArrayList<>();
        for (SearchHit searchHit : hits1) {
            String sourceAsString = searchHit.getSourceAsString();
            Goods goods = JSON.parseObject(sourceAsString, Goods.class);
            goodsList.add(goods);
        }
        for (Goods goods : goodsList) {
            System.out.println(goods);
        }
    }

}
