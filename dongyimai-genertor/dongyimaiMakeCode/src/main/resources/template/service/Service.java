package ${package_service};
import ${package_pojo}.${Table};
import com.baomidou.mybatisplus.extension.service.IService;
import com.offcn.entity.PageResult;
import java.util.List;
/****
 * @Author:ujiuye
 * @Description:${Table}业务层接口
 * @Date 2021/2/1 14:19
 *****/

public interface ${Table}Service extends IService<${Table}> {

    /***
     * ${Table}多条件分页查询
     * @param ${table}
     * @param page
     * @param size
     * @return
     */
    PageResult<${Table}> findPage(${Table} ${table}, int page, int size);

    /***
     * ${Table}分页查询
     * @param page
     * @param size
     * @return
     */
    PageResult<${Table}> findPage(int page, int size);

    /***
     * ${Table}多条件搜索方法
     * @param ${table}
     * @return
     */
    List<${Table}> findList(${Table} ${table});

    /***
     * 删除${Table}
     * @param id
     */
    void delete(${keyType} id);

    /***
     * 修改${Table}数据
     * @param ${table}
     */
    void update(${Table} ${table});

    /***
     * 新增${Table}
     * @param ${table}
     */
    void add(${Table} ${table});

    /**
     * 根据ID查询${Table}
     * @param id
     * @return
     */
     ${Table} findById(${keyType} id);

    /***
     * 查询所有${Table}
     * @return
     */
    List<${Table}> findAll();
}
