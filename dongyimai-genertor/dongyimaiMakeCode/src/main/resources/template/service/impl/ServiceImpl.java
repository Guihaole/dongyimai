package ${package_service_impl};
import ${package_mapper}.${Table}Mapper;
import ${package_pojo}.${Table};
import ${package_service}.${Table}Service;
import com.github.pagehelper.PageHelper;
import com.offcn.entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
/****
 * @Author:ujiuye
 * @Description:${Table}业务层接口实现类
 * @Date 2021/2/1 14:19
 *****/
@Service
public class ${Table}ServiceImpl extends ServiceImpl<${Table}Mapper,${Table}> implements ${Table}Service {


    /**
     * ${Table}条件+分页查询
     * @param ${table} 查询条件
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageResult<${Table}> findPage(${Table} ${table}, int page, int size){
         Page<${Table}> mypage = new Page<>(page, size);
        QueryWrapper<${Table}> queryWrapper = this.createQueryWrapper(${table});
        IPage<${Table}> iPage = this.page(mypage, queryWrapper);
        return new PageResult<${Table}>(iPage.getTotal(),iPage.getRecords());
    }

    /**
     * ${Table}分页查询
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageResult<${Table}> findPage(int page, int size){
        Page<${Table}> mypage = new Page<>(page, size);
        IPage<${Table}> iPage = this.page(mypage, new QueryWrapper<${Table}>());

        return new PageResult<${Table}>(iPage.getTotal(),iPage.getRecords());
    }

    /**
     * ${Table}条件查询
     * @param ${table}
     * @return
     */
    @Override
    public List<${Table}> findList(${Table} ${table}){
        //构建查询条件
        QueryWrapper<${Table}> queryWrapper = this.createQueryWrapper(${table});
        //根据构建的条件查询数据
        return this.list(queryWrapper);
    }


    /**
     * ${Table}构建查询对象
     * @param ${table}
     * @return
     */
    public QueryWrapper<${Table}> createQueryWrapper(${Table} ${table}){
        QueryWrapper<${Table}> queryWrapper = new QueryWrapper<>();
        if(${table}!=null){
            <#list models as md>
            // ${md.desc}
            if(!StringUtils.isEmpty(${table}.get${md.upperName}())){
                <#if (md.name?contains("title") || md.name?contains("name") || md.name?contains("address") )>
                queryWrapper.like("${md.column}",${table}.get${md.upperName}());
                <#else>
                 queryWrapper.eq("${md.column}",${table}.get${md.upperName}());
                </#if>
            }
            </#list>
        }
        return queryWrapper;
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(${keyType} id){
        this.removeById(id);
    }

    /**
     * 修改${Table}
     * @param ${table}
     */
    @Override
    public void update(${Table} ${table}){
        this.updateById(${table});
    }

    /**
     * 增加${Table}
     * @param ${table}
     */
    @Override
    public void add(${Table} ${table}){
        this.save(${table});
    }

    /**
     * 根据ID查询${Table}
     * @param id
     * @return
     */
    @Override
    public ${Table} findById(${keyType} id){
        return  this.getById(id);
    }

    /**
     * 查询${Table}全部数据
     * @return
     */
    @Override
    public List<${Table}> findAll() {
        return this.list(new QueryWrapper<${Table}>());
    }
}
