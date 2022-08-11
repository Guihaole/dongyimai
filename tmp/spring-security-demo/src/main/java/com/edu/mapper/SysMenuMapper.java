package com.edu.mapper;

import com.edu.pojo.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author offcn
 * @since 2021-11-16
 */

public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<SysMenu> selectMenuPermsByUserId(Integer id);
}
