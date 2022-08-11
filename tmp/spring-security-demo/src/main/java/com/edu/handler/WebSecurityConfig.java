package com.edu.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.stereotype.Component;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig<main> extends WebSecurityConfigurerAdapter {

    @Autowired
    //登录失败操作 指的是密码或者用户名错误
    private SecurityAuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    //不携带用户名或者密码的非法访问资源，（称之为匿名访问）
    private AuthenticationEntryPointHandler authenticationEntryPoint;

    @Autowired
    //认证通过后没有权限
    private SecurityAccessDeniedHandler accessDeniedHandler;

    //自定义用户名和密码
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * 配置密码解析
     * @return
     */
    @Bean
    protected PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置用户名和密码
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    /**
     * 需求：自定义
     * 1. 登录页面
     * 2. 登录失败页面
     * 3. 登录成功页面
     * 4. 退出登录页面 及其跳转
     * 5. 放行页面
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //关闭csrf防护 跨站请求防护
        http.csrf().disable()
                //表单登录
                .formLogin()
                //登录页面
                .loginPage("/login.html")
                //登录访问路径，与页面表单提交路径一致
                .loginProcessingUrl("/login")
                //登录成功后访问路径
                .defaultSuccessUrl("/index.html").permitAll()
                //登录失败操作 指的是密码或者用户名错误
                .failureHandler(authenticationFailureHandler)
                .and()
                //认证配置
                .authorizeRequests()
                .antMatchers("/login.html", "/login").permitAll()
                //配置静态页面可以访问
                .antMatchers("/js/**", "/css/**", "/images/**", "/favicon.ico").permitAll()
                //任何请求
                .anyRequest()
                //都需要身份验证
                .authenticated();
        //配置无权限访问页面，没有权限需要跳转的页面
        //http.exceptionHandling().accessDeniedPage("/uanuth.html");
        http.exceptionHandling()
                //不携带用户名或者密码的非法访问资源，（称之为匿名访问）
                .authenticationEntryPoint(authenticationEntryPoint)
                //认证通过后没有权限
                .accessDeniedHandler(accessDeniedHandler);
        //配置退出
        http.logout()
                //退出路径
                .logoutUrl("/logout")
                //退出后跳转页面
                .logoutSuccessUrl("/login.html");
    }

}
