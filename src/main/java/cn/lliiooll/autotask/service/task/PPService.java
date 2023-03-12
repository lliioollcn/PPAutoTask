package cn.lliiooll.autotask.service.task;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.lliiooll.autotask.data.pojo.UserData;
import cn.lliiooll.autotask.data.pojo.UserTask;
import cn.lliiooll.autotask.data.service.UserService;
import cn.lliiooll.autotask.service.TaskLogService;
import cn.lliiooll.autotask.service.TaskService;
import cn.lliiooll.autotask.task.zuiyouLite.TaskZuiyouLite;
import cn.lliiooll.autotask.task.zuiyouLite.data.*;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
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

    @Override
    public void doTask(UserService service, UserTask task, UserData data) {
        try {
            String cookie = task.getCookie();
            JSONObject json = JSONUtil.parseObj(cookie);
            task.setAccount(json.getStr("nickname"));
            task.setTaskStatus(1);
            service.updateUserTask(task);
            FileWriter fileWriter = taskLogService.createLog(task.getId());
            fileWriter.write("开始运行皮皮搞笑任务\n");
            log.info("开始运行皮皮搞笑任务\n");
            fileWriter.flush();
            // 开始任务
            fileWriter.write("开始初始化token\n");
            fileWriter.flush();
            TaskZuiyouLite taskApi = TaskZuiyouLite.newTask(task.getCookie());
            // 签到
            fileWriter.write("开始签到\n");
            fileWriter.flush();
            ZyCommonData<ZyCheckInData> cid = taskApi.checkIn();
            if (cid.getRet() == -1) {
                fileWriter.write("签到失败,原因: " + cid.getMsg() + "\n");
                fileWriter.flush();
            } else {
                if (cid.getData() == null){
                    fileWriter.write("签到失败: 已经签到过了\n");
                }else {
                    fileWriter.write("签到成功，获得以下物品:\n");
                    fileWriter.flush();
                    for (ZyCheckInData.ZyCheckInDataItem item : cid.getData().getList()) {
                        fileWriter.write("  >>物品: " + item.getName() + " *" + item.getCount() + "\n");
                        fileWriter.flush();
                    }
                }
            }
            fileWriter.write("开始获取馒头奖励\n");
            fileWriter.flush();
            ZyCommonData<ZyGetBubblesData> gbd = taskApi.getBubbles();
            if (gbd.getRet() == -1) {
                fileWriter.write("奖励获取失败,原因: " + gbd.getMsg() + "\n");
                fileWriter.flush();
            } else {
                for (ZyGetBubblesData.ZyGetBubblesDataItem item : gbd.getData().getList()) {
                    if (item.getType() != 3) {
                        String name = StrUtil.isBlank(item.getTask_name()) ? "刮刮乐" : item.getTask_name();
                        fileWriter.write("开始收取奖励: " + name + "\n");
                        fileWriter.flush();
                        ZyCommonData<ZyGetRewardData> grd = taskApi.getBubbleReward(item);
                        if (grd.getRet() == -1) {
                            fileWriter.write("奖励收取失败,原因: " + gbd.getMsg() + "\n");
                            fileWriter.flush();
                        } else {
                            fileWriter.write("奖励收取成功: " + name + "\n");
                            fileWriter.flush();
                        }
                    }

                }
            }
            fileWriter.write("开始获取背包物品\n");
            fileWriter.flush();
            List<ZyGetPackData> gpd = taskApi.getAllPackItems();
            // 注意offset和limit
            if (gpd.size() < 1) {
                fileWriter.write("背包物品获取失败,原因: " + taskApi.getPackItems().getMsg() + "\n");
                fileWriter.flush();
            } else {
                fileWriter.write("开始遍历背包物品\n");
                fileWriter.flush();
                for (ZyGetPackData pack : gpd) {
                    for (ZyGetPackData.ZyGetPackDataItem item : pack.getList()) {
                        if (item.getKind_type() == 7) {
                            fileWriter.write("找到未打开的刮刮乐，准备打开\n");
                            fileWriter.flush();
                            if (item.getExpired_t() * 1000 < System.currentTimeMillis()) {
                                fileWriter.write("打开失败: 刮刮乐已过期\n");
                                fileWriter.flush();
                            } else {

                                ZyCommonData<ZyOpenBoxV2Data> obv2d = taskApi.openBoxV2(item);
                                if (obv2d.getRet() == -1) {
                                    fileWriter.write("打开失败,原因: " + obv2d.getMsg() + "\n");
                                    fileWriter.flush();
                                } else {
                                    fileWriter.write("打开成功，获得 " + obv2d.getData().getCount_to_award() + "个 物品:\n");
                                    fileWriter.flush();
                                    for (ZyOpenBoxV2Data.ZyOpenBoxV2DataItem bitem : obv2d.getData().getList()) {
                                        fileWriter.write("  >>获得: " + bitem.getName() + " *" + bitem.getCount() + "\n");
                                        fileWriter.flush();
                                    }
                                }
                            }

                        }
                    }
                }

            }
            fileWriter.write("皮皮搞笑任务结束\n");
            log.info("皮皮搞笑任务结束\n");
            fileWriter.flush();
            fileWriter.close();
            task.setLastTime(System.currentTimeMillis());
            task.setTaskStatus(0);
            service.updateUserTask(task);
        } catch (Throwable e) {
            task.setTaskStatus(-1);
            service.updateUserTask(task);
            if (System.currentTimeMillis() - task.getLastTime() > 1000 * 60 * 60 * 24) {
                taskService.notifyFailed(task);
            }
            task.setLastTime(Long.MAX_VALUE);
            e.printStackTrace();
        }

    }
}
