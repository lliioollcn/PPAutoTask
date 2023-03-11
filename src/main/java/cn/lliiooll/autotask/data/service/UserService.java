package cn.lliiooll.autotask.data.service;

import cn.lliiooll.autotask.data.pojo.UserData;
import cn.lliiooll.autotask.data.pojo.UserTask;

import java.util.List;

public interface UserService {
    List<UserData> selectAllUserData();

    List<UserTask> selectAllUserTask();

    List<UserTask> selectUserTaskByMid(String mid);

    /**
     * @param mid
     * @return 用户是否存在
     */
    boolean checkUserByMid(String mid);

    /**
     * @param email
     * @return 用户是否存在
     */
    boolean checkUserByEmail(String email);

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

    List<UserTask> selectAllUserTask(int limit, int offset);

    int selectUserTaskTotal();
    int selectUserDataTotal();
    List<UserData> selectAllUserData(int limit, int offset);
}
