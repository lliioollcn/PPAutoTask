package cn.lliiooll.autotask.interceptor;

import cn.hutool.core.util.StrUtil;
import cn.lliiooll.autotask.data.web.AjaxCodes;
import cn.lliiooll.autotask.data.web.AjaxResult;
import cn.lliiooll.autotask.service.AuthService;
import cn.lliiooll.autotask.utils.NetUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final AuthService authService;

    @Autowired
    public AuthInterceptor(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setCharacterEncoding("utf-8");
        String token = request.getHeader("Token");
        if (request.getRequestURI().startsWith("/auth") || request.getRequestURI().startsWith("/error")) {
            return true;
        }
        if (StrUtil.isNotBlank(token)) {
            if (authService.auth(token, NetUtils.getIpAddr(request))) {
                return true;
            }
        }
        response.setStatus(403);
        response.getWriter().write(AjaxResult.builder().status(AjaxCodes.NEED_AUTH).msg("请重新登录").build().toString());
        return false;


    }
}
