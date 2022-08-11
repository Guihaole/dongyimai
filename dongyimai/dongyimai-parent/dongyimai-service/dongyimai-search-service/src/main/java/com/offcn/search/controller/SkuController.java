package com.offcn.search.controller;

import com.offcn.entity.Result;
import com.offcn.entity.StatusCode;
import com.offcn.search.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/search")
@CrossOrigin
public class SkuController {

    @Autowired
    private SkuService skuService;

    /**
     * 调用itemFegin接口将回收到的数据存入搜索引擎
     */
    @GetMapping("/import")
    public Result search(){
        skuService.importSku();
        return new Result(true, StatusCode.OK,"导入搜索引擎数据成功");
    }

    /**
     * 需求: 根据前端传递过来的json关键字
     * 去搜索引擎中检索响应的数据
     * url: http://localhost:9005/search  参数json串
     * @return
     */
    @GetMapping
    public Map searchSku(@RequestParam(required = false) Map searchMap){
        return skuService.searchSku(searchMap);
    }
    @GetMapping("/hello")
    public void hello(){
        System.out.println("提供者已经进入");
    }
}
