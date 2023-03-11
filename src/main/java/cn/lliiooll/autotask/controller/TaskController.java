package cn.lliiooll.autotask.controller;

import cn.hutool.core.util.StrUtil;
import cn.lliiooll.autotask.data.web.AjaxCodes;
import cn.lliiooll.autotask.data.web.AjaxResult;
import cn.lliiooll.autotask.service.TaskService;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task")
public class TaskController {

    private TaskService taskService;
    private HttpServletRequest request;

    @Autowired
    public TaskController(TaskService taskService, HttpServletRequest request) {
        this.taskService = taskService;
        this.request = request;
    }


    @GetMapping("/supports")
    public AjaxResult supports() {
        return AjaxResult.builder().status(AjaxCodes.SUCCESS).msg("查询成功").data(taskService.supports()).build();
    }

    @GetMapping("/user")
    public AjaxResult user() {
        return AjaxResult.builder().status(AjaxCodes.SUCCESS).msg("查询成功").data(taskService.user(request)).build();
    }

    @GetMapping("/details")
    public AjaxResult details(String type) {
        if (!StrUtil.isNumeric(type)) {
            return AjaxResult.builder().status(AjaxCodes.FAILED).msg("查询失败").data(null).build();

        }
        return AjaxResult.builder().status(AjaxCodes.SUCCESS).msg("查询成功").data(taskService.details(Integer.parseInt(type))).build();
    }
}
