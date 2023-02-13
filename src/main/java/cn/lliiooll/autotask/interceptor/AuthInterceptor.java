package cn.lliiooll.autotask.interceptor;

import cn.hutool.core.util.StrUtil;
import cn.lliiooll.autotask.data.web.AjaxCodes;
import cn.lliiooll.autotask.data.web.AjaxResult;
import cn.lliiooll.autotask.service.AuthService;
import cn.lliiooll.autotask.utils.NetUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
@AllArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final AuthService authService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        if (request.getMethod().equalsIgnoreCase("GET") || request.getMethod().equalsIgnoreCase("POST")) {
            String token = request.getHeader("Token");
            if (request.getRequestURI().startsWith("/auth") || request.getRequestURI().startsWith("/error")) {
                return true;
            }
            if (StrUtil.isNotBlank(token)) {
                if (authService.auth(token, NetUtils.getIpAddr(request))) {
                    return true;
                }
                log.info("验证失败,token无效");
            }
            response.getWriter().write(AjaxResult.builder().status(AjaxCodes.NEED_AUTH).msg("请重新登录").build().toString());
            return false;
        }
        return true;


    }
}
