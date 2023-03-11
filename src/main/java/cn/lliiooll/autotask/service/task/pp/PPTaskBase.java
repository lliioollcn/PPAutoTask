package cn.lliiooll.autotask.service.task.pp;

import java.io.FileWriter;

public abstract class PPTaskBase {

    public abstract boolean doTask(String token, FileWriter writer);
}
