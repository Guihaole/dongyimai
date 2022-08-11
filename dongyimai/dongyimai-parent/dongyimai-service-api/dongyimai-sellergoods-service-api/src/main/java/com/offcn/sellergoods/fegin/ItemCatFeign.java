package com.offcn.sellergoods.fegin;

import com.offcn.entity.Result;
import com.offcn.sellergoods.pojo.ItemCat;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "dym-sellergoods",path = "/itemCat")
public interface ItemCatFeign {
    @GetMapping("/{id}")
    public Result<ItemCat> findById(@PathVariable(value = "id") Long id);
}
