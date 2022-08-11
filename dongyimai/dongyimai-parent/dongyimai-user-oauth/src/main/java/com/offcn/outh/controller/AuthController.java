package com.offcn.outh.controller;

import com.offcn.entity.Result;
import com.offcn.entity.StatusCode;
import com.offcn.outh.service.AuthService;
import com.offcn.outh.util.AuthToken;
import com.offcn.outh.util.CookieUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;


/*
http://localhost/?code=MZowqf

 */

@RestController
@RequestMapping(value = "/user")
public class AuthController {

    //客户端ID
    @Value("${outh.clientId}")
    private String clientId;

    //秘钥
    @Value("${outh.clientSecret}")
    private String clientSecret;

    //Cookie存储的域名
    @Value("${outh.cookieDomain}")
    private String cookieDomain;

    //Cookie生命周期
    @Value("${outh.cookieMaxAge}")
    private int cookieMaxAge;

    @Resource
    AuthService authService;
    //http://localhost:9100/user/login?username....
    @PostMapping("/login")
    public Result login(String username, String password,String from) {
        System.out.println("------AuthController----username----"+username);
        System.out.println("------AuthController----password----"+password);
        System.out.println("------AuthController----from----"+from);
        if(StringUtils.isEmpty(username)){
            throw new RuntimeException("用户名不允许为空");
        }
        if(StringUtils.isEmpty(password)){
            throw new RuntimeException("密码不允许为空");
        }
        System.out.println("------开始申请令牌----");
        //申请令牌
        AuthToken authToken =  authService.login(username,password,clientId,clientSecret);

        //用户身份令牌
        String access_token = authToken.getAccessToken();
        //将令牌存储到cookie
        saveCookie(access_token,from);

        return new Result(true, StatusCode.OK,"登录成功！");
    }

    /***
     * 将令牌存储到cookie
     * @param token
     */
    private void saveCookie(String token,String from){
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        CookieUtil.addCookie(response,cookieDomain,"/","Authorization",token,cookieMaxAge,false);
        response.setHeader("Refresh","3;URL=http://localhost:8001"+from);//3秒后刷新页面 访问新的地址 from
    }
}
