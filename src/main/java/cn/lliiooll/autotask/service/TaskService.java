package cn.lliiooll.autotask.service;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import cn.lliiooll.autotask.data.bean.UserTaskBean;
import cn.lliiooll.autotask.data.pojo.SysTask;
import cn.lliiooll.autotask.data.pojo.UserTask;
import cn.lliiooll.autotask.data.service.SysService;
import cn.lliiooll.autotask.data.service.UserService;
import cn.lliiooll.autotask.service.task.BaseTaskService;
import cn.lliiooll.autotask.service.task.PPService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TaskService {

    private SysService sysService;
    private UserService userService;
    private AuthService authService;
    private TaskLogService taskLogService;

    private final Map<Integer, BaseTaskService> taskServices = new ConcurrentHashMap<>();

    @Autowired
    public TaskService(SysService sysService, UserService userService, AuthService authService, TaskLogService taskLogService) {
        this.sysService = sysService;
        this.userService = userService;
        this.authService = authService;
        this.taskLogService = taskLogService;
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

    public void register(BaseTaskService service, int type, String name) {
        if (sysService.selectTaskByTaskType(type) == null) {
            sysService.insertTask(SysTask.builder()
                    .taskName(name)
                    .taskType(type)
                    .build());
        }
        taskServices.put(type, service);
    }

    public Object start(int id, HttpServletRequest request) {
        String token = request.getHeader("Token");
        String mid = this.authService.getUserMid(token);
        for (UserTask task : userService.selectUserTaskByMid(mid)) {
            if (task.getId() == id && task.getTaskStatus() != 1) {
                BaseTaskService taskService = taskServices.get(task.getTaskType());
                if (taskService != null) {
                    ThreadUtil.execute(() -> taskService.doTask(userService, task, userService.selectUserDataByMid(task.getMid())));
                }
            }
        }
        return null;
    }

    public String log(int id, HttpServletRequest request) {
        String token = request.getHeader("Token");
        String mid = this.authService.getUserMid(token);
        for (UserTask task : userService.selectUserTaskByMid(mid)) {
            if (task.getId() == id && task.getTaskStatus() != 1) {
                return this.taskLogService.readLog(id).replace("\n","<br/>");
            }
        }
        return "";
    }
}
