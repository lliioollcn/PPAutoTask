package cn.lliiooll.autotask.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.lliiooll.autotask.data.bean.OpenTaskViewBean;
import cn.lliiooll.autotask.data.bean.OpenUserTaskBean;
import cn.lliiooll.autotask.data.pojo.UserData;
import cn.lliiooll.autotask.data.pojo.UserTask;
import cn.lliiooll.autotask.data.service.SysService;
import cn.lliiooll.autotask.data.service.UserService;
import cn.lliiooll.autotask.data.web.AjaxCodes;
import cn.lliiooll.autotask.data.web.AjaxResult;
import cn.lliiooll.autotask.service.AuthService;
import cn.lliiooll.autotask.service.SafeService;
import cn.lliiooll.autotask.service.TaskLogService;
import cn.lliiooll.autotask.utils.NetUtils;
import cn.lliiooll.autotask.utils.RedisUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/open")
public class OpenController {

    private SafeService safeService;
    private HttpServletRequest request;
    private UserService userService;
    private SysService sysService;
    private TaskLogService logService;

    @Autowired
    public OpenController(TaskLogService logService, SysService sysService, SafeService safeService, HttpServletRequest request, UserService userService) {
        this.safeService = safeService;
        this.request = request;
        this.userService = userService;
        this.logService = logService;
        this.sysService = sysService;
    }

    @GetMapping("/tasks")
    public AjaxResult tasks(String email) {
        /*
        if (safeService.limit(request, email))
            return AjaxResult.builder().status(AjaxCodes.FAILED).msg("请求速率过快，请稍后再试").data(null).build();
        */
        if (StrUtil.isNotBlank(email)) {

            UserData data = userService.selectUserDataByEmail(email);
            if (data != null) {
                List<UserTask> tasks = userService.selectUserTaskByMid(data.getMid());
                List<OpenUserTaskBean> datas = new ArrayList<>();
                for (UserTask task : tasks) {
                    datas.add(OpenUserTaskBean.builder()
                            .account(task.getAccount())
                            .id(task.getId())
                            .taskType(task.getTaskType())
                            .taskStatus(task.getTaskStatus())
                            .mid(task.getMid())
                            .taskName(sysService.selectTaskByTaskType(task.getTaskType()).getTaskName())
                            .lastTime(task.getLastTime())
                            .log(logService.readLog(task.getId()))
                            .build());
                }
                return AjaxResult.builder().status(AjaxCodes.SUCCESS).msg("查询成功").data(datas.toArray()).build();
            }
        }
        return AjaxResult.builder().status(AjaxCodes.FAILED).msg("请求参数错误").data(null).build();
    }

    @GetMapping("/total")
    public AjaxResult total() {
        /*
        if (safeService.limit(request))
            return AjaxResult.builder().status(AjaxCodes.FAILED).msg("请求速率过快，请稍后再试").data(null).build();


         */
        return AjaxResult.builder().status(AjaxCodes.SUCCESS).msg("查询成功").data(new HashMap<String, Object>() {{
            put("task", userService.selectUserTaskTotal());
            put("user", userService.selectUserDataTotal());
        }}).build();
    }

    @GetMapping("/view")
    public AjaxResult view(int page) {
        /*
        if (safeService.limit(request))
            return AjaxResult.builder().status(AjaxCodes.FAILED).msg("请求速率过快，请稍后再试").data(null).build();
         */
        List<UserTask> userTasks = userService.selectAllUserTask(12, page * 12);

        List<OpenTaskViewBean> datas = new ArrayList<>();
        for (UserTask task : userTasks) {
            UserData data = userService.selectUserDataByMid(task.getMid());
            String name = replace(task.getAccount());
            String email = replace(data.getEmail());
            datas.add(OpenTaskViewBean.builder()
                    .name(name)
                    .email(email)
                    .last(DateUtil.format(new Date(task.getLastTime()), "yyyy-MM-dd H:mm:ss"))
                    .status(task.getTaskStatus())
                    .task(sysService.selectTaskByTaskType(task.getTaskType()).getTaskName())
                    .build());
        }
        return AjaxResult.builder().status(AjaxCodes.SUCCESS).msg("查询成功").data(new HashMap<String, Object>() {{
            put("total", userService.selectUserTaskTotal());
            put("page", page);
            put("list", datas.toArray());
        }}).build();
    }

    private String replace(String name) {
        StringBuilder sb = new StringBuilder();
        if (name.contains("@")) {
            String[] emailS = name.split("@");
            for (int i = 0; i < emailS[0].length(); i++) {
                sb.append("*");
            }
            sb.append("@").append(emailS[1]);
        } else {
            sb.append(name.length() / 2 < 1 ? "" : append(name.length() / 2))
                    .append(name.substring(Math.max(name.length() / 2, 1)));
        }
        return sb.toString();
    }

    private String append(int i) {
        StringBuilder sb = new StringBuilder();
        for (int q = 0; q < i; q++) {
            sb.append("*");
        }
        return sb.toString();
    }
}
