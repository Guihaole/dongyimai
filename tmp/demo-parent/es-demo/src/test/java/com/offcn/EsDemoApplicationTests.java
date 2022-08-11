package com.offcn;

import com.alibaba.fastjson.JSON;
import com.offcn.mapper.GoodsMapper;
import com.offcn.pojo.Goods;
import com.offcn.pojo.Person;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class EsDemoApplicationTests {
	@Autowired
	private RestHighLevelClient restHighLevelClient;
	@Autowired
	private GoodsMapper goodsMapper;
	@Test
	void contextLoads() {
		System.out.println(restHighLevelClient);
	}

	/**
	 * 创建索引
	 * @throws IOException
	 */
	@Test
	public void addIndex() throws IOException {
		//1.获取索引对象
		IndicesClient indices = restHighLevelClient.indices();
		CreateIndexRequest createIndexRequest = new CreateIndexRequest("offcn");
		CreateIndexResponse createIndexResponse = indices.create(createIndexRequest, RequestOptions.DEFAULT);
		System.out.println(createIndexResponse.isAcknowledged());
	}

	/**
	 * 创建mapping数据结构
	 */
	@Test
	public void addMapping() throws IOException {
		IndicesClient indices = restHighLevelClient.indices();
		CreateIndexRequest createIndexRequest = new CreateIndexRequest("offcn2");
		//设置mappings
		String mapping = "{\n" +
				"      \"properties\" : {\n" +
				"        \"address\" : {\n" +
				"          \"type\" : \"text\",\n" +
				"          \"analyzer\" : \"ik_max_word\"\n" +
				"        },\n" +
				"        \"age\" : {\n" +
				"          \"type\" : \"long\"\n" +
				"        },\n" +
				"        \"name\" : {\n" +
				"          \"type\" : \"keyword\"\n" +
				"        }\n" +
				"      }\n" +
				"    }";
		createIndexRequest.mapping(mapping, XContentType.JSON);
		CreateIndexResponse createIndexResponse = indices.create(createIndexRequest, RequestOptions.DEFAULT);
		System.out.println(createIndexResponse.isAcknowledged());

	}
	/**
	 * 查询索引
	 */
	@Test
	public void queryIndex() throws IOException {
		IndicesClient indices = restHighLevelClient.indices();
		GetIndexRequest getIndexRequest = new GetIndexRequest("offcn2");
		GetIndexResponse indexResponse = indices.get(getIndexRequest, RequestOptions.DEFAULT);
		Map<String, MappingMetaData> mappings = indexResponse.getMappings();
		for (String key : mappings.keySet()) {
			System.out.println(key + ":" + mappings.get(key).getSourceAsMap());
		}
	}
	/**
	 * 删除索引
	 */
	@Test
	public void deleteIndex() throws IOException {
		IndicesClient indices = restHighLevelClient.indices();
		DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("offcn");
		AcknowledgedResponse acknowledgedResponse = indices.delete(deleteIndexRequest, RequestOptions.DEFAULT);
		System.out.println(acknowledgedResponse.isAcknowledged());
	}
	/**
	 * 判断索引是否存在
	 */
	@Test
	public void existIndex() throws IOException {
		IndicesClient indices = restHighLevelClient.indices();
		GetIndexRequest getIndexRequest = new GetIndexRequest("offcn");
		boolean exists = indices.exists(getIndexRequest, RequestOptions.DEFAULT);
		System.out.println(exists);

	}
	/**
	 * 添加文档,使用map作为数据
	 */
	@Test
	public void addDoc() throws IOException {
		//数据对象，map
		Map data = new HashMap();
		data.put("address", "北京昌平");
		data.put("name", "马同志");
		data.put("age", 20);
		//1.构建添加对象
		IndexRequest indexRequest = new IndexRequest("offcn");
		indexRequest.id("1").source(data);
		IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
		System.out.println(indexResponse.getId());
	}
	/**
	 * 添加文档,使用对象作为数据
	 */
	@Test
	public void addDoc2() throws IOException {
		//数据对象，javaObject
		Person p = new Person();
		p.setId("2");
		p.setName("小胖2222");
		p.setAge(30);
		p.setAddress("陕西西安");
		//JSON
		String s = JSON.toJSONString(p);
		//添加文档
		IndexRequest indexRequest = new IndexRequest("offcn");
		indexRequest.id(p.getId()).source(s,XContentType.JSON);
		IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
		System.out.println(indexResponse.getId());
	}
	/**
	 * 修改文档：添加文档时，如果id存在则修改，id不存在则添加
	 */
	@Test
	public void updateDoc() throws IOException {
		//数据对象，map
		Map data = new HashMap();
		data.put("address", "北京昌平");
		data.put("name", "朱同志");
		data.put("age", 20);
		IndexRequest indexRequest = new IndexRequest("offcn");
		indexRequest.id("1").source(data);
		IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
		System.out.println(indexResponse.getId());
	}
	/**
	 * 根据id查询文档
	 */
	@Test
	public void findDocById() throws IOException {
		GetRequest getRequest = new GetRequest("offcn", "1");
		GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
		System.out.println(getResponse.getSourceAsString());
	}
	/**
	 * 根据id删除文档
	 */
	@Test
	public void delDoc() throws IOException {
		DeleteRequest deleteRequest = new DeleteRequest("offcn", "1");
		DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
		System.out.println(deleteResponse.getId());
	}
	//	POST _bulk
	//	{"delete":{"_index":"person1","_id":"4"}}
	//	{"create":{"_index":"person1","_id":"5"}}
	//	{"name":"八号","age":18,"address":"北京"}
	//	{"update":{"_index":"person1","_id":"2"}}
	//	{"doc":{"name":"2号"}}
	/**
	 * 批量添加
	 */
	@Test
	public void test2() throws IOException {
		BulkRequest bulkRequest = new BulkRequest();
		//删除
		DeleteRequest deleteRequest = new DeleteRequest("person1", "4");
		bulkRequest.add(deleteRequest);
		//添加
		HashMap<String, Object> map = new HashMap<>();
		map.put("name","八号");
		map.put("age","18");
		map.put("address","北京");
		IndexRequest indexRequest = new IndexRequest("person1");
		indexRequest.id("5").source(map);
		bulkRequest.add(indexRequest);
		//修改
		HashMap<String, Object> updateMap = new HashMap<>();
		updateMap.put("name","2号");
		UpdateRequest updateRequest = new UpdateRequest("person1","2").doc(updateMap);
		bulkRequest.add(updateRequest);
		//执行批量操作
		BulkResponse bulkItemResponses = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
		System.out.println(bulkItemResponses.status());
	}

	/**
	 * 从Mysql 批量导入 elasticSearch
	 */
	@Test
	public void test3() throws IOException {
		//1.查询所有数据，mysql
		List<Goods> goodsList = goodsMapper.selectList(null);

		for (Goods goods : goodsList) {
			String spec = goods.getSpec();
			Map map = JSON.parseObject(spec, Map.class);
			goods.setSpec(null);
			goods.setMap(map);
			//将对象转为json
			String data = JSON.toJSONString(goods);
			//1:获取操作文档的对象
			IndexRequest request = new IndexRequest("goods")
                    //Double--->String
					.id(String.valueOf(goods.getId().intValue()))
					.source(data, XContentType.JSON);
			//2:添加数据，获取结果
			IndexResponse response = restHighLevelClient.index(request, RequestOptions.DEFAULT);
		}
	}

}
