package cn.lliiooll.autotask.data.service.impl;

import cn.lliiooll.autotask.data.mapper.UserMapper;
import cn.lliiooll.autotask.data.pojo.UserData;
import cn.lliiooll.autotask.data.pojo.UserTask;
import cn.lliiooll.autotask.data.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserMapper mapper;

    @Autowired
    public UserServiceImpl(UserMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<UserData> selectAllUserData() {
        return mapper.selectAllUserData();
    }

    @Override
    public List<UserTask> selectAllUserTask() {
        return mapper.selectAllUserTask();
    }

    @Override
    public List<UserTask> selectUserTaskByMid(String mid) {
        return mapper.selectUserTaskByMid(mid);
    }

    /**
     * @param mid
     * @return 用户是否存在
     */
    @Override
    public boolean checkUserByMid(String mid) {
        return selectUserDataByMid(mid) != null;
    }

    /**
     * @param email
     * @return 用户是否存在
     */
    @Override
    public boolean checkUserByEmail(String email) {
        return selectUserDataByEmail(email) != null;
    }

    @Override
    public UserData selectUserDataByMid(String mid) {
        return mapper.selectUserDataByMid(mid);
    }

    @Override
    public UserData selectUserDataByEmail(String email) {
        return mapper.selectUserDataByEmail(email);
    }

    @Override
    public UserTask selectUserTaskByTaskType(int taskType) {
        return mapper.selectUserTaskByTaskType(taskType);
    }

    @Override
    public void insertUserData(UserData data) {
        mapper.insertUserData(data);
    }

    @Override
    public void updateUserData(UserData data) {
        mapper.updateUserData(data);
    }

    @Override
    public void insertUserTask(UserTask task) {
        mapper.insertUserTask(task);
    }

    @Override
    public void updateUserTask(UserTask task) {
        mapper.updateUserTask(task);
    }

    @Override
    public void deleteUserDataByMid(String mid) {
        mapper.deleteUserDataByMid(mid);
    }

    @Override
    public void deleteUserTaskByMid(String mid) {
        mapper.deleteUserTaskByMid(mid);
    }

    @Override
    public void deleteUserTaskById(int id) {
        mapper.deleteUserTaskById(id);
    }

    @Override
    public List<UserTask> selectAllUserTask(int limit, int offset) {
        return mapper.selectLimitUserTask(limit, offset);
    }

    @Override
    public int selectUserTaskTotal() {
        return mapper.selectUserTaskTotal();
    }

    @Override
    public int selectUserDataTotal() {
        return mapper.selectUserDataTotal();
    }

    @Override
    public List<UserData> selectAllUserData(int limit, int offset) {
        return mapper.selectLimitUserData(limit, offset);
    }

}
