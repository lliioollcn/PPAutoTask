package cn.lliiooll.autotask.controller;

import cn.hutool.core.util.StrUtil;
import cn.lliiooll.autotask.data.pojo.UserData;
import cn.lliiooll.autotask.data.pojo.UserTask;
import cn.lliiooll.autotask.data.service.UserService;
import cn.lliiooll.autotask.data.web.AjaxCodes;
import cn.lliiooll.autotask.data.web.AjaxResult;
import cn.lliiooll.autotask.service.AuthService;
import cn.lliiooll.autotask.service.SafeService;
import cn.lliiooll.autotask.utils.NetUtils;
import cn.lliiooll.autotask.utils.RedisUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/open")
public class OpenController {

    private SafeService safeService;
    private HttpServletRequest request;
    private UserService userService;

    @Autowired
    public OpenController(SafeService safeService, HttpServletRequest request, UserService userService) {
        this.safeService = safeService;
        this.request = request;
        this.userService = userService;
    }

    @GetMapping("/tasks")
    public AjaxResult tasks(String email) {
        if (safeService.limit(request, email))
            return AjaxResult.builder().status(AjaxCodes.FAILED).msg("请求速率过快，请稍后再试").data(null).build();
        if (StrUtil.isNotBlank(email)) {
            UserData data = userService.selectUserDataByEmail(email);
            if (data != null) {
                List<UserTask> tasks = userService.selectUserTaskByMid(data.getMid());
                for (UserTask task : tasks){
                    task.setCookie("");
                }
                return AjaxResult.builder().status(AjaxCodes.SUCCESS).msg("查询成功").data(tasks.toArray()).build();
            }
        }
        return AjaxResult.builder().status(AjaxCodes.FAILED).msg("请求参数错误").data(null).build();
    }
}
