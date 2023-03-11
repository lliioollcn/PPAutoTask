package cn.lliiooll.autotask.controller;

import cn.hutool.core.util.StrUtil;
import cn.lliiooll.autotask.data.web.AjaxCodes;
import cn.lliiooll.autotask.data.web.AjaxResult;
import cn.lliiooll.autotask.service.TaskService;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/log")
    public AjaxResult log(int id) {
        // 返回任务日志，判断这个任务是不是这个账号的，并且判断这个任务不是在运行中
        return AjaxResult.builder().status(AjaxCodes.SUCCESS).msg("查询成功").data(null).build();
    }

    @GetMapping("/start")
    public AjaxResult start(int id) {
        // 启动任务，判断这个任务是不是这个账号的，并且判断这个任务不是在运行中
        return AjaxResult.builder().status(AjaxCodes.SUCCESS).msg("查询成功").data(taskService.start(id, request)).build();
    }

    @GetMapping("/delete")
    public AjaxResult delete(int id) {
        // 删除任务，判断这个任务是不是这个账号的
        return AjaxResult.builder().status(AjaxCodes.SUCCESS).msg("查询成功").data(null).build();
    }

    @PostMapping("/edit")
    public AjaxResult edit(@RequestBody String data) {
        // 更新Cookie
        return AjaxResult.builder().status(AjaxCodes.SUCCESS).msg("查询成功").data(null).build();
    }

    @GetMapping("/details")
    public AjaxResult details(String type) {
        if (!StrUtil.isNumeric(type)) {
            return AjaxResult.builder().status(AjaxCodes.FAILED).msg("查询失败").data(null).build();

        }
        return AjaxResult.builder().status(AjaxCodes.SUCCESS).msg("查询成功").data(taskService.details(Integer.parseInt(type))).build();
    }
}
