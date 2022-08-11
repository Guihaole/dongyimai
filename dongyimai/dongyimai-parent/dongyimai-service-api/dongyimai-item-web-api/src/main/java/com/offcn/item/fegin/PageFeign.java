package com.offcn.item.fegin;

import com.offcn.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "ITEM-WEB",path = "/page")
public interface PageFeign {
    //生成html的fegin接口，用于canal服务调用
    @RequestMapping("/createHtml/{id}")
    public Result createHtml(@PathVariable(value="id") Long id);
}
