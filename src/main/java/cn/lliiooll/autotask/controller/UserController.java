package cn.lliiooll.autotask.controller;

import cn.lliiooll.autotask.data.web.AjaxCodes;
import cn.lliiooll.autotask.data.web.AjaxResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/status")
    public AjaxResult status() {
        return AjaxResult.builder().status(AjaxCodes.SUCCESS).msg("登录成功").data(null).build();
    }
}
