package cn.lliiooll.autotask.data.service;

import cn.lliiooll.autotask.data.pojo.UserData;
import cn.lliiooll.autotask.data.pojo.UserTask;

import java.util.List;

public interface UserService {
    List<UserData> selectAllUserData();

    List<UserTask> selectAllUserTask();

    UserTask selectUserTaskByMid(String mid);
    boolean checkUserByMid(String mid);
    boolean checkUserByEmail(String email);

    UserData selectUserDataByMid(String mid);

    UserData selectUserDataByEmail(String email);

    UserTask selectUserTaskByTaskType(int taskType);

    void insertUserData(UserData data);

    void updateUserData(UserData data);

    void insertUserTask(UserTask task);

    void updateUserTask(UserTask task);

    void deleteUserDataByMid(String mid);

    void deleteUserTaskById(String mid);
}
