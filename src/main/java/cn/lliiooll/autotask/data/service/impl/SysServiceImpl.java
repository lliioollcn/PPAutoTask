package cn.lliiooll.autotask.data.service.impl;

import cn.lliiooll.autotask.data.mapper.SysMapper;
import cn.lliiooll.autotask.data.mapper.UserMapper;
import cn.lliiooll.autotask.data.pojo.SysTask;
import cn.lliiooll.autotask.data.service.SysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SysServiceImpl implements SysService {
    private final SysMapper mapper;

    @Autowired
    public SysServiceImpl(SysMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<SysTask> selectAllTask() {
        return mapper.selectAllTask();
    }

    @Override
    public SysTask selectTaskByTaskType(int taskType) {
        return mapper.selectTaskByTaskType(taskType);
    }

    @Override
    public void insertTask(SysTask task) {
        mapper.insertTask(task);
    }

    @Override
    public void updateTask(SysTask task) {
        mapper.updateTask(task);
    }

    @Override
    public void deleteTask(SysTask task) {
        mapper.deleteTask(task);
    }
}
