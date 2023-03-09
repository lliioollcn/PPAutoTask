package cn.lliiooll.autotask.data.mapper;

import cn.lliiooll.autotask.data.pojo.SysTask;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysMapper {

    List<SysTask> selectAllTask();

    SysTask selectTaskByTaskType(int taskType);

    void insertTask(SysTask task);

    void updateTask(SysTask task);

    void deleteTask(SysTask task);

}
