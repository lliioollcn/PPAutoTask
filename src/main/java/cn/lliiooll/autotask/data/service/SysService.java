package cn.lliiooll.autotask.data.service;

import cn.lliiooll.autotask.data.pojo.SysTask;

import java.util.List;

public interface SysService {

    List<SysTask> selectAllTask();

    SysTask selectTaskByTaskType(int taskType);

    void insertTask(SysTask task);

    void updateTask(SysTask task);

    void deleteTask(SysTask task);
}
