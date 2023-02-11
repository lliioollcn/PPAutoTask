package cn.lliiooll.autotask.controller;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.lliiooll.autotask.data.bean.AuthBean;
import cn.lliiooll.autotask.data.bean.LoginRespBean;
import cn.lliiooll.autotask.data.web.AjaxCodes;
import cn.lliiooll.autotask.data.web.AjaxResult;
import cn.lliiooll.autotask.service.AuthService;
import cn.lliiooll.autotask.utils.NetUtils;
import cn.lliiooll.autotask.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final HttpServletRequest request;


    @RequestMapping("/")
    public AjaxResult index() {
        return AjaxResult.builder().status(AjaxCodes.NEED_AUTH).msg("请重新登录").data(null).build();
    }

    @PostMapping("/register")
    public AjaxResult register(@RequestBody String jstr) {
        if (Utils.isValidBody(jstr, AuthBean.class)) {
            AuthBean bean = JSONUtil.toBean(jstr, AuthBean.class);
            if (StrUtil.isAllNotBlank(bean.getEmail(), bean.getPassWord(), bean.getToken(), bean.getServer())
                    && ReUtil.isMatch("[-|a-z0-9A-Z._]+@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\\\.)+[a-z]{2,}$", bean.getEmail())
                    && ReUtil.isMatch("(?!\\d+$)(?!^[a-zA-Z]+$)(?!^[_#@]+$).{8,20}", bean.getPassWord())) {
                if (authService.vaptcha(bean.getToken(), bean.getServer(), NetUtils.getIpAddr(request))) {
                    if (authService.register(bean)) {
                        return AjaxResult.builder().status(AjaxCodes.SUCCESS).msg("注册成功，请在您的邮箱中检查激活邮件并进行下一步").data(null).build();
                    }
                    return AjaxResult.builder().status(AjaxCodes.FAILED).msg("您已经注册过了").data(null).build();
                }
            } else {
                return AjaxResult.builder().status(AjaxCodes.FAILED).msg("邮箱/密码 不符合要求").data(null).build();
            }
        }
        return AjaxResult.builder().status(AjaxCodes.FAILED).msg("服务器错误").data(null).build();
    }

    @PostMapping("/login")
    public AjaxResult login(@RequestBody String jstr) {
        if (Utils.isValidBody(jstr, AuthBean.class)) {
            AuthBean bean = JSONUtil.toBean(jstr, AuthBean.class);
            if (StrUtil.isAllNotBlank(bean.getEmail(), bean.getPassWord(), bean.getToken(), bean.getServer())) {
                if (authService.vaptcha(bean.getToken(), bean.getServer(), NetUtils.getIpAddr(request))) {
                    LoginRespBean login = authService.login(bean,NetUtils.getIpAddr(request));
                    if (login != null) {
                        return AjaxResult.builder().status(AjaxCodes.SUCCESS).msg("成功").data(login).build();
                    } else {
                        return AjaxResult.builder().status(AjaxCodes.FAILED).msg("用户不存在或密码错误").data(null).build();
                    }

                }
            }
        }
        return AjaxResult.builder().status(AjaxCodes.FAILED).msg("服务器错误").data(null).build();
    }

    @RequestMapping("/verify/{code}")
    public AjaxResult verify(String code) {
        if (authService.verify(code)) {
            return AjaxResult.builder().status(AjaxCodes.SUCCESS).msg("验证完毕，现在你可以登录你的账号了").data(null).build();
        }
        return AjaxResult.builder().status(AjaxCodes.FAILED).msg("服务器错误").data(null).build();
    }
}
