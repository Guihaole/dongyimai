package com.offcn.sellergoods.service;
import com.offcn.sellergoods.pojo.FreightTemplate;
import com.baomidou.mybatisplus.extension.service.IService;
import com.offcn.entity.PageResult;
import java.util.List;
/****
 * @Author:ujiuye
 * @Description:FreightTemplate业务层接口
 * @Date 2021/2/1 14:19
 *****/

public interface FreightTemplateService extends IService<FreightTemplate> {

    /***
     * FreightTemplate多条件分页查询
     * @param freightTemplate
     * @param page
     * @param size
     * @return
     */
    PageResult<FreightTemplate> findPage(FreightTemplate freightTemplate, int page, int size);

    /***
     * FreightTemplate分页查询
     * @param page
     * @param size
     * @return
     */
    PageResult<FreightTemplate> findPage(int page, int size);

    /***
     * FreightTemplate多条件搜索方法
     * @param freightTemplate
     * @return
     */
    List<FreightTemplate> findList(FreightTemplate freightTemplate);

    /***
     * 删除FreightTemplate
     * @param id
     */
    void delete(Long id);

    /***
     * 修改FreightTemplate数据
     * @param freightTemplate
     */
    void update(FreightTemplate freightTemplate);

    /***
     * 新增FreightTemplate
     * @param freightTemplate
     */
    void add(FreightTemplate freightTemplate);

    /**
     * 根据ID查询FreightTemplate
     * @param id
     * @return
     */
     FreightTemplate findById(Long id);

    /***
     * 查询所有FreightTemplate
     * @return
     */
    List<FreightTemplate> findAll();
}
