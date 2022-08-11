package com.offcn.controller;


import com.offcn.search.fegin.SkuFeign;
import com.offcn.search.pojo.SkuInfo;
import com.offcn.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/search")
public class SkuController {

    @Autowired
    private SkuFeign skuFeign;

    /**
     * 搜索
     * @param searchMap
     * url:http://localhost:9101/search/list
     * @return
     */
    @GetMapping(value = "/list")
    public String search(@RequestParam(required = false) Map searchMap, Model model){
        Map resultMap = skuFeign.searchSku(searchMap);
        //搜索数据结果
        model.addAttribute("result",resultMap);
        //搜索条件
        model.addAttribute("searchMap",searchMap);
        //4.记住之前的URL
        //拼接url
        String url = this.setUrl(searchMap);
        model.addAttribute("url",url);
        //5.创建一个分页的对象  可以获取当前页 和总个记录数和显示的页码(以当前页为中心的5个页码)
        Page<SkuInfo> infoPage=null;
        if(resultMap.containsKey("pageNum")){
            infoPage = new Page<SkuInfo>(
                    Long.valueOf(resultMap.get("total").toString()),
                    Integer.valueOf(resultMap.get("pageNum").toString()),
                    Integer.valueOf(resultMap.get("pageSize").toString())
            );
        }
        model.addAttribute("page",infoPage);
        return "search";
    }
    private String setUrl(Map<String, String> searchMap) {
        String url = "/search/list";
        if(searchMap!=null && searchMap.size()>0){
            url+="?";
            for (Map.Entry<String, String> stringStringEntry : searchMap.entrySet()) {
                String key = stringStringEntry.getKey();// keywords / brand  / category
                String value = stringStringEntry.getValue();//华为  / 华为  / 笔记本sortField
                if(stringStringEntry.getKey().equals("sortField")){
                    continue;
                }
                if(stringStringEntry.getKey().equals("sortRule")){
                    continue;
                }
                if(stringStringEntry.getKey().equals("pageNum")){
                    continue;
                }
                url+=key+"="+value+"&";
            }

            //去掉多余的&
            if(url.lastIndexOf("&")!=-1){
                url =  url.substring(0,url.lastIndexOf("&"));
            }

        }
        return url;
    }
}
