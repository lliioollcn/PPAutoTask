package cn.lliiooll.autotask.data.mapper;

import cn.lliiooll.autotask.data.pojo.SysTask;
import cn.lliiooll.autotask.data.pojo.UserData;
import cn.lliiooll.autotask.data.pojo.UserTask;

import java.util.List;

public interface SysMapper {

    List<SysTask> selectAllTask();

    SysTask selectTaskByTaskType(int taskType);

    void insertTask(SysTask task);

    void updateTask(SysTask task);

    void deleteTask(SysTask task);

}
