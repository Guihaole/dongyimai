package com.edu.services;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.edu.mapper.SysMenuMapper;
import com.edu.mapper.SysRoleMapper;
import com.edu.mapper.SysUserMapper;
import com.edu.pojo.SysMenu;
import com.edu.pojo.SysRole;
import com.edu.pojo.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    //权限表
    @Autowired
    private SysMenuMapper sysMenuMapper;

    //角色表
    @Autowired
    private SysRoleMapper sysRoleMapper;

    //用户表
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //1.根据用户名查询用户信息
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name",username);
        SysUser dbUser = sysUserMapper.selectOne(wrapper);
        if (dbUser==null) {
            throw new  UsernameNotFoundException("用户不存在!");
        }
        System.out.println(dbUser);
        //存放权限集合
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        //2.查询用户所拥有的角色
        List<SysRole> sysRoles = sysRoleMapper.selectRoleCodesByUserId(dbUser.getId());
        for (SysRole sysRole : sysRoles) {
            //客户端使用的角色名称
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + sysRole.getRoleCode());
            authorities.add(authority);
        }
        //3.查询用户所拥有的权限
        List<SysMenu> sysMenus = sysMenuMapper.selectMenuPermsByUserId(dbUser.getId());
        for (SysMenu sysMenu : sysMenus) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(sysMenu.getPerms());
            authorities.add(authority);
        }
        return new User(dbUser.getUserName(),dbUser.getPassword(),authorities);
    }
}
