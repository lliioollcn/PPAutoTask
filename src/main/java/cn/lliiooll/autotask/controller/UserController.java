package cn.lliiooll.autotask.controller;

import cn.lliiooll.autotask.data.pojo.UserData;
import cn.lliiooll.autotask.data.pojo.UserTask;
import cn.lliiooll.autotask.data.service.UserService;
import cn.lliiooll.autotask.data.web.AjaxCodes;
import cn.lliiooll.autotask.data.web.AjaxResult;
import cn.lliiooll.autotask.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private AuthService authService;
    private HttpServletRequest request;

    private UserService userService;

    @Autowired
    public UserController(AuthService authService, HttpServletRequest request, UserService userService) {
        this.authService = authService;
        this.request = request;
        this.userService = userService;
    }

    @GetMapping("/status")
    public AjaxResult status() {
        String token = request.getHeader("Token");
        String mid = this.authService.getUserMid(token);
        UserData data = userService.selectUserDataByMid(mid);
        data.setPassWord("");
        return AjaxResult.builder().status(AjaxCodes.SUCCESS).msg("登录成功").data(data.getEmailAuthed()).build();
    }
}
