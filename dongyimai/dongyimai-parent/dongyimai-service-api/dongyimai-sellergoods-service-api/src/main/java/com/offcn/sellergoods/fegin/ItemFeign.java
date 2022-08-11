package com.offcn.sellergoods.fegin;

import com.offcn.entity.Result;
import com.offcn.sellergoods.pojo.Item;
import org.mockito.stubbing.ValidableAnswer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "DYM-SELLERGOODS",path = "/item")
public interface ItemFeign {
    @GetMapping("/status/{status}")
    Result<List<Item>> findByStatus(@PathVariable("status")String status);
    /***
     * 根据ID查询Item数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Item> findById(@PathVariable(value = "id") Long id);

    /**
     * 扣减库存接口
     * @param username
     * @return
     */
//    @PostMapping(value = "/decr/count/{username}")
//    public Result decrCount(@PathVariable(value = "username") String username);
    @PostMapping(value = "/decr/count")
    public Result decrCount(@RequestParam(value = "username") String username);
}
