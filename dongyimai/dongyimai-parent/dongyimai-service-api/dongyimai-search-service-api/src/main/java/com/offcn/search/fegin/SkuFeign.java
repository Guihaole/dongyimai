package com.offcn.search.fegin;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(value = "SEARCH",path = "/search")
public interface SkuFeign {
    /**
     * 搜索
     * @param searchMap
     * @return
     */
    @GetMapping
    public Map searchSku(@RequestParam(value = "searchMap",required = false) Map searchMap);
    //亲测服务可以调通
    @GetMapping("/hello")
    public void hello();
}
