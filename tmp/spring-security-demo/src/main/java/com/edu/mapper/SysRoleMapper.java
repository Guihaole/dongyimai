package com.edu.mapper;

import com.edu.pojo.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author offcn
 * @since 2021-11-16
 */

public interface SysRoleMapper extends BaseMapper<SysRole> {

    List<SysRole> selectRoleCodesByUserId(Integer id);
}
