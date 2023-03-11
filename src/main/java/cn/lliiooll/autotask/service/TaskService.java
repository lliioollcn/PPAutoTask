package cn.lliiooll.autotask.service;

import cn.hutool.core.util.StrUtil;
import cn.lliiooll.autotask.data.bean.UserTaskBean;
import cn.lliiooll.autotask.data.pojo.SysTask;
import cn.lliiooll.autotask.data.service.SysService;
import cn.lliiooll.autotask.data.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TaskService {

    private SysService sysService;
    private UserService userService;
    private AuthService authService;

    @Autowired
    public TaskService(SysService sysService, UserService userService, AuthService authService) {
        this.sysService = sysService;
        this.userService = userService;
        this.authService = authService;
    }


    public SysTask[] supports() {
        return sysService.selectAllTask().toArray(new SysTask[0]);
    }

    public UserTaskBean[] user(HttpServletRequest request) {
        String token = request.getHeader("Token");
        String mid = this.authService.getUserMid(token);
        if (mid == null || StrUtil.isBlank(mid)) {
            return new UserTaskBean[0];
        }
        return new ArrayList<UserTaskBean>() {{
            userService.selectUserTaskByMid(mid).forEach(task -> {
                add(UserTaskBean.builder()
                        .taskStatus(task.getTaskStatus())
                        .id(task.getId())
                        .taskType(task.getTaskType())
                        .mid(task.getMid())
                        .lastTime(task.getLastTime())
                        .taskName(details(task.getTaskType()).getTaskName())
                        .account(task.getAccount())
                        .build());
            });
        }}.toArray(new UserTaskBean[0]);
    }

    public SysTask details(int type) {
        return sysService.selectTaskByTaskType(type);
    }
}
