package cn.lliiooll.autotask.controller;

import cn.hutool.json.JSONUtil;
import cn.lliiooll.autotask.data.bean.AuthBean;
import cn.lliiooll.autotask.data.bean.LoginRespBean;
import cn.lliiooll.autotask.data.web.AjaxCodes;
import cn.lliiooll.autotask.data.web.AjaxResult;
import cn.lliiooll.autotask.service.AuthService;
import cn.lliiooll.autotask.utils.NetUtils;
import cn.lliiooll.autotask.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final HttpServletRequest request;

    @Autowired
    public AuthController(AuthService authService, HttpServletRequest request) {
        this.authService = authService;
        this.request = request;
    }

    @RequestMapping("/")
    public AjaxResult index() {
        return AjaxResult.builder().status(AjaxCodes.NEED_AUTH).msg("请重新登录").data(null).build();
    }

    @PostMapping("/register")
    public AjaxResult register(@RequestBody String jstr) {
        if (Utils.isValidBody(jstr, AuthBean.class)) {
            AuthBean bean = JSONUtil.toBean(jstr, AuthBean.class);
            if (authService.vaptcha(bean.getToken(), bean.getServer(), NetUtils.getIpAddr(request))) {
                if (authService.register(bean)) {
                    return AjaxResult.builder().status(AjaxCodes.SUCCESS).msg("注册成功，请在您的邮箱中检查激活邮件并进行下一步").data(null).build();
                }
                return AjaxResult.builder().status(AjaxCodes.FAILED).msg("您已经注册过了").data(null).build();
            }
        }
        return AjaxResult.builder().status(AjaxCodes.FAILED).msg("服务器错误").data(null).build();
    }

    @PostMapping("/register")
    public AjaxResult login(@RequestBody String jstr) {
        if (Utils.isValidBody(jstr, AuthBean.class)) {
            AuthBean bean = JSONUtil.toBean(jstr, AuthBean.class);
            if (authService.vaptcha(bean.getToken(), bean.getServer(), NetUtils.getIpAddr(request))) {
                return AjaxResult.builder().status(AjaxCodes.SUCCESS).msg("成功").data(authService.login(bean)).build();
            }
        }
        return AjaxResult.builder().status(AjaxCodes.FAILED).msg("服务器错误").data(null).build();
    }


}
