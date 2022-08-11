package com.offcn.gateway.filter;


import com.offcn.gateway.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

//网关过滤器
@Component
public class AuthorizeFilter implements GlobalFilter, Ordered {
    //令牌名字
    private static final String AUTHORIZE_TOKEN = "Authorization";
    //打回登录页面
    private static final String USER_LOGIN_URL = "http://localhost:9100/oauth/login";
    /**
     * 全局过滤器
     *
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        //获取请求URI
        String path = request.getURI().getPath();
        //如果是登录、goods等开放的微服务[这里的goods部分开放],则直接放行,这里不做完整演示，完整演示需要设计一套权限系统
        if (URLFilter.hasAuthorize(path)) {
            // 放行
            return chain.filter(exchange);
        }
//        if (path.startsWith("/api/user/login") || path.startsWith("/api/brand/search/")) {
//            //放行
//            Mono<Void> filter = chain.filter(exchange);
//            return filter;
//        }
        //1.获取请求头的令牌
        String tokent = request.getHeaders().getFirst(AUTHORIZE_TOKEN);
        System.out.println("======== 获取请求头的令牌====" + tokent);
        //2.获取参数的令牌
        if (StringUtils.isEmpty(tokent)) {
            tokent = request.getQueryParams().getFirst(AUTHORIZE_TOKEN);
        }
        //3. 获取cookie的令牌
        if (StringUtils.isEmpty(tokent)) {
            HttpCookie first = request.getCookies().getFirst(AUTHORIZE_TOKEN);
            if (first != null) {
                tokent = first.getValue();
            }
        }
        //如果为null 则输出错误的代码
        if (StringUtils.isEmpty(tokent)) {
            //response.setStatusCode(HttpStatus.METHOD_NOT_ALLOWED);
            //response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return needAuthorization(USER_LOGIN_URL, exchange, path);
        }
        //        try {
        //            Claims claims = JwtUtil.parseJWT(tokent);
        //            //将令牌数据添加到头文件中
        //            request.mutate().header(AUTHORIZE_TOKEN,claims.toString());
        //        } catch (Exception e) {
        //            e.printStackTrace();
        //            //解析失败
        //            response.setStatusCode(HttpStatus.UNAUTHORIZED);
        //            return response.setComplete();
        //        }
        if (!tokent.startsWith("bearer ") && !tokent.startsWith("Bearer ")) {
            tokent = "bearer " + tokent;
        }
        request.mutate().header(AUTHORIZE_TOKEN, tokent);
        return chain.filter(exchange);
    }

    /**
     * 响应设置
     * @param userLoginUrl
     * @param exchange
     * @param from
     * @return
     */
    private Mono<Void> needAuthorization(String userLoginUrl, ServerWebExchange exchange, String from) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.SEE_OTHER);
        // url - 跳转的登录模块地址
        // from - 将跳转前的地址记录, 方便登录成功后再自动跳回来
        response.getHeaders().set("Location",userLoginUrl+"?from="+from);
        return exchange.getResponse().setComplete();
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
