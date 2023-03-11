package cn.lliiooll.autotask.service.task;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.lliiooll.autotask.data.pojo.SysTask;
import cn.lliiooll.autotask.data.pojo.UserData;
import cn.lliiooll.autotask.data.pojo.UserTask;
import cn.lliiooll.autotask.data.service.UserService;
import cn.lliiooll.autotask.service.TaskLogService;
import cn.lliiooll.autotask.service.TaskService;
import cn.lliiooll.autotask.service.task.pp.PPTaskBase;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Service
public class PPService extends BaseTaskService {

    private TaskService taskService;
    private TaskLogService taskLogService;

    @Autowired
    public PPService(TaskService taskService, TaskLogService taskLogService) {
        this.taskService = taskService;
        this.taskLogService = taskLogService;
    }

    @PostConstruct
    public void init() {
        taskService.register(this, 0, "皮皮搞笑");
    }

    @SneakyThrows
    @Override
    public void doTask(UserService service, UserTask task, UserData data) {
        String cookie = task.getCookie();
        JSONObject json = JSONUtil.parseObj(cookie);
        task.setAccount(json.getStr("nickname"));
        task.setTaskStatus(1);
        service.updateUserTask(task);
        FileWriter fileWriter = taskLogService.createLog(task.getId());
        fileWriter.write("开始运行皮皮搞笑任务\n");
        fileWriter.flush();
        for (PPTaskBase pTask : tasks) {
            pTask.doTask(cookie, fileWriter);
            fileWriter.flush();
        }
        fileWriter.write("皮皮搞笑任务结束\n");
        fileWriter.flush();
        fileWriter.close();
        task.setLastTime(System.currentTimeMillis());
        task.setTaskStatus(0);
        service.updateUserTask(task);
    }

    private final Set<PPTaskBase> tasks = new CopyOnWriteArraySet<>();

    public void register(PPTaskBase ppTask) {
        tasks.add(ppTask);
    }
}
