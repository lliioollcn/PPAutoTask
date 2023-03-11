package cn.lliiooll.autotask.service;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import cn.lliiooll.autotask.data.bean.UserTaskBean;
import cn.lliiooll.autotask.data.pojo.SysTask;
import cn.lliiooll.autotask.data.pojo.UserData;
import cn.lliiooll.autotask.data.pojo.UserTask;
import cn.lliiooll.autotask.data.service.SysService;
import cn.lliiooll.autotask.data.service.UserService;
import cn.lliiooll.autotask.service.task.BaseTaskService;
import cn.lliiooll.autotask.service.task.PPService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class TaskService {

    private SysService sysService;
    private UserService userService;
    private AuthService authService;
    private TaskLogService taskLogService;
    private MailService mailService;

    private final Map<Integer, BaseTaskService> taskServices = new ConcurrentHashMap<>();

    @Autowired
    public TaskService(MailService mailService, SysService sysService, UserService userService, AuthService authService, TaskLogService taskLogService) {
        this.sysService = sysService;
        this.userService = userService;
        this.authService = authService;
        this.taskLogService = taskLogService;
        this.mailService = mailService;
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
                return this.taskLogService.readLog(id).replace("\n", "<br/>");
            }
        }
        return "";
    }

    public String delete(int id, HttpServletRequest request) {
        String token = request.getHeader("Token");
        String mid = this.authService.getUserMid(token);
        for (UserTask task : userService.selectUserTaskByMid(mid)) {
            if (task.getId() == id) {
                userService.deleteUserTaskById(task.getId());
                return "删除完毕";
            }
        }
        return "";
    }

    public String edit(HttpServletRequest request, int id, String cookie) {
        String token = request.getHeader("Token");
        String mid = this.authService.getUserMid(token);
        if (userService.selectUserDataByMid(mid).getEmailAuthed() == 1) {
            for (UserTask task : userService.selectUserTaskByMid(mid)) {
                if (task.getId() == id) {
                    task.setCookie(cookie);
                    userService.updateUserTask(task);
                    return "保存成功";
                }
            }
        }
        return "";
    }

    public SysTask[] sys(HttpServletRequest request) {
        String token = request.getHeader("Token");
        String mid = this.authService.getUserMid(token);
        if (userService.selectUserDataByMid(mid).getEmailAuthed() == 1) {
            return sysService.selectAllTask().toArray(new SysTask[0]);
        }
        return new SysTask[0];
    }

    public String add(HttpServletRequest request, int taskType, String cookie) {
        String token = request.getHeader("Token");
        String mid = this.authService.getUserMid(token);
        if (userService.selectUserDataByMid(mid).getEmailAuthed() == 1) {
            userService.insertUserTask(UserTask.builder()
                    .taskType(taskType)
                    .cookie(cookie)
                    .taskStatus(0)
                    .lastTime(0)
                    .account("未知")
                    .mid(mid)
                    .createTime(System.currentTimeMillis())
                    .build());
        }
        return "";
    }

    @Scheduled(cron = "0 0 0/8 1/1 * ?")
    public void startAllTask() {
        userService.selectAllUserTask().forEach(task -> {
            BaseTaskService taskService = taskServices.get(task.getTaskType());
            if (taskService != null) {
                log.info("开始运行任务");
                ThreadUtil.execute(() -> taskService.doTask(userService, task, userService.selectUserDataByMid(task.getMid())));
            }
        });
    }

    public void notifyFailed(UserTask task) {
        UserData data = userService.selectUserDataByMid(task.getMid());
        if (data.getEmailAuthed() == 1)
            mailService.sendFailedMail(data.getEmail());
    }
}
