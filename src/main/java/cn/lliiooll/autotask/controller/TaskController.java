package cn.lliiooll.autotask.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
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

    @GetMapping("/sys")
    public AjaxResult sys() {
        return AjaxResult.builder().status(AjaxCodes.SUCCESS).msg("查询成功").data(taskService.sys(request)).build();
    }

    @GetMapping("/log")
    public AjaxResult log(int id) {
        // 返回任务日志，判断这个任务是不是这个账号的，并且判断这个任务不是在运行中
        return AjaxResult.builder().status(AjaxCodes.SUCCESS).msg("查询成功").data(taskService.log(id, request)).build();
    }

    @GetMapping("/start")
    public AjaxResult start(int id) {
        // 启动任务，判断这个任务是不是这个账号的，并且判断这个任务不是在运行中
        return AjaxResult.builder().status(AjaxCodes.SUCCESS).msg("查询成功").data(taskService.start(id, request)).build();
    }

    @GetMapping("/delete")
    public AjaxResult delete(int id) {
        // 删除任务，判断这个任务是不是这个账号的
        return AjaxResult.builder().status(AjaxCodes.SUCCESS).msg("查询成功").data(taskService.delete(id, request)).build();
    }

    @PostMapping("/edit")
    public AjaxResult edit(@RequestBody String data) {
        // 更新Cookie
        if (StrUtil.isNotBlank(data) && JSONUtil.isTypeJSON(data)) {
            JSONObject json = JSONUtil.parseObj(data);
            int id = json.getInt("id");
            String cookie = json.getStr("cookie");
            if (!JSONUtil.isTypeJSON(cookie))
                return AjaxResult.builder().status(AjaxCodes.FAILED).msg("无效的token格式，token应为json格式，包含h_av、token等字段").data(null).build();
            JSONObject jsonObject = JSONUtil.parseObj(cookie);
            if (!jsonObject.containsKey("h_av")
                    || !jsonObject.containsKey("h_pipi")
                    || !jsonObject.containsKey("h_os")
                    || !jsonObject.containsKey("h_dt")
                    || !jsonObject.containsKey("h_app")
                    || !jsonObject.containsKey("h_model")
                    || !jsonObject.containsKey("h_did")
                    || !jsonObject.containsKey("token")
                    || !jsonObject.containsKey("android_id")
            )
                return AjaxResult.builder().status(AjaxCodes.FAILED).msg("无效的token格式，token应为json格式，包含userstatus、nickname等字段").data(null).build();

            if (StrUtil.isNotBlank(cookie))
                return AjaxResult.builder().status(AjaxCodes.SUCCESS).msg("保存成功").data(taskService.edit(request, id, cookie)).build();
        }
        return AjaxResult.builder().status(AjaxCodes.FAILED).msg("数据不能为空").data(null).build();
    }

    @PostMapping("/add")
    public AjaxResult add(@RequestBody String data) {
        // 更新Cookie
        if (StrUtil.isNotBlank(data) && JSONUtil.isTypeJSON(data)) {
            JSONObject json = JSONUtil.parseObj(data);
            int taskType = json.getInt("id");
            String cookie = json.getStr("cookie");
            if (!JSONUtil.isTypeJSON(cookie))
                return AjaxResult.builder().status(AjaxCodes.FAILED).msg("无效的token格式，token应为json格式，包含h_av、token等字段").data(null).build();
            JSONObject jsonObject = JSONUtil.parseObj(cookie);
            if (!jsonObject.containsKey("h_av")
                    || !jsonObject.containsKey("h_pipi")
                    || !jsonObject.containsKey("h_os")
                    || !jsonObject.containsKey("h_dt")
                    || !jsonObject.containsKey("h_app")
                    || !jsonObject.containsKey("h_model")
                    || !jsonObject.containsKey("h_did")
                    || !jsonObject.containsKey("token")
                    || !jsonObject.containsKey("android_id")
            )
                return AjaxResult.builder().status(AjaxCodes.FAILED).msg("无效的token格式，token应为json格式，包含userstatus、nickname等字段").data(null).build();
            if (StrUtil.isNotBlank(cookie))
                return AjaxResult.builder().status(AjaxCodes.SUCCESS).msg("添加成功").data(taskService.add(request, taskType, cookie)).build();
        }
        return AjaxResult.builder().status(AjaxCodes.FAILED).msg("数据不能为空").data(null).build();
    }

    @GetMapping("/details")
    public AjaxResult details(String type) {
        if (!StrUtil.isNumeric(type)) {
            return AjaxResult.builder().status(AjaxCodes.FAILED).msg("查询失败").data(null).build();

        }
        return AjaxResult.builder().status(AjaxCodes.SUCCESS).msg("查询成功").data(taskService.details(Integer.parseInt(type))).build();
    }
}
