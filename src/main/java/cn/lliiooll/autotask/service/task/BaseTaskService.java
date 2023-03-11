package cn.lliiooll.autotask.service.task;

import cn.lliiooll.autotask.data.pojo.UserData;
import cn.lliiooll.autotask.data.pojo.UserTask;
import cn.lliiooll.autotask.data.service.UserService;

public abstract class BaseTaskService {

    public abstract void doTask(UserService userService,UserTask task, UserData data);
}
