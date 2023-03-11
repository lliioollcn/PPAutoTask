package cn.lliiooll.autotask.data.mapper;

import cn.lliiooll.autotask.data.pojo.UserData;
import cn.lliiooll.autotask.data.pojo.UserTask;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    List<UserData> selectAllUserData();

    List<UserTask> selectAllUserTask();

    List<UserTask> selectUserTaskByMid(String mid);

    UserData selectUserDataByMid(String mid);

    UserData selectUserDataByEmail(String email);

    UserTask selectUserTaskByTaskType(int taskType);

    void insertUserData(UserData data);

    void updateUserData(UserData data);

    void insertUserTask(UserTask task);

    void updateUserTask(UserTask task);

    void deleteUserDataByMid(String mid);

    void deleteUserTaskByMid(String mid);
    void deleteUserTaskById(int id);

}
