package com.offcn.search.dao;

import com.offcn.search.pojo.SkuInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


@Mapper
//这里的Long类型是SkuInfo中的id,代表指定那个字段存入es
public interface SkuEsMapper extends ElasticsearchRepository<SkuInfo,Long> {
}
