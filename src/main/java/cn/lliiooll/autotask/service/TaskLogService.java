package cn.lliiooll.autotask.service;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;

@Service
public class TaskLogService {

    @SneakyThrows
    public FileWriter createLog(int taskId) {
        File logDir = new File("./temp/log");
        if (!logDir.exists()) {
            logDir.mkdirs();
        }
        File logFile = new File(logDir, taskId + ".txt");
        if (!logFile.exists()) {
            logFile.exists();
        }
        return new FileWriter(logFile);
    }

}
