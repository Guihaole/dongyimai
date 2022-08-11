package com.offcn.sellergoods.service;
import com.offcn.sellergoods.pojo.Specification;
import com.baomidou.mybatisplus.extension.service.IService;
import com.offcn.entity.PageResult;
import java.util.List;
/****
 * @Author:ujiuye
 * @Description:Specification业务层接口
 * @Date 2021/2/1 14:19
 *****/

public interface SpecificationService extends IService<Specification> {

    /***
     * Specification多条件分页查询
     * @param specification
     * @param page
     * @param size
     * @return
     */
    PageResult<Specification> findPage(Specification specification, int page, int size);

    /***
     * Specification分页查询
     * @param page
     * @param size
     * @return
     */
    PageResult<Specification> findPage(int page, int size);

    /***
     * Specification多条件搜索方法
     * @param specification
     * @return
     */
    List<Specification> findList(Specification specification);

    /***
     * 删除Specification
     * @param id
     */
    void delete(Long id);

    /***
     * 修改Specification数据
     * @param specification
     */
    void update(Specification specification);

    /***
     * 新增Specification
     * @param specification
     */
    void add(Specification specification);

    /**
     * 根据ID查询Specification
     * @param id
     * @return
     */
     Specification findById(Long id);

    /***
     * 查询所有Specification
     * @return
     */
    List<Specification> findAll();
}
