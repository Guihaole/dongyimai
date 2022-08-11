package com.offcn.sellergoods.service;
import com.offcn.sellergoods.pojo.Item;
import com.baomidou.mybatisplus.extension.service.IService;
import com.offcn.entity.PageResult;
import java.util.List;
/****
 * @Author:ujiuye
 * @Description:Item业务层接口
 * @Date 2021/2/1 14:19
 *****/

public interface ItemService extends IService<Item> {

    /***
     * 库存递减
     * @param username
     */
    void decrCount(String username);
    /***
     * Item多条件分页查询
     * @param item
     * @param page
     * @param size
     * @return
     */
    PageResult<Item> findPage(Item item, int page, int size);

    /***
     * Item分页查询
     * @param page
     * @param size
     * @return
     */
    PageResult<Item> findPage(int page, int size);

    /***
     * Item多条件搜索方法
     * @param item
     * @return
     */
    List<Item> findList(Item item);

    /***
     * 删除Item
     * @param id
     */
    void delete(Long id);

    /***
     * 修改Item数据
     * @param item
     */
    void update(Item item);

    /***
     * 新增Item
     * @param item
     */
    void add(Item item);

    /**
     * 根据ID查询Item
     * @param id
     * @return
     */
     Item findById(Long id);

    /***
     * 查询所有Item
     * @return
     */
    List<Item> findAll();

    List<Item> findByStatus(String status);
}
