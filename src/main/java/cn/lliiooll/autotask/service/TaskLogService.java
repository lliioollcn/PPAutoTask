package cn.lliiooll.autotask.service;

import cn.hutool.core.io.FileUtil;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;

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
        return new FileWriter(logFile, StandardCharsets.UTF_8);
    }

    @SneakyThrows
    public String readLog(int taskId) {
        File logDir = new File("./temp/log");
        if (!logDir.exists()) {
            logDir.mkdirs();
            return "";
        }
        File logFile = new File(logDir, taskId + ".txt");
        if (!logFile.exists()) {
            logFile.exists();
            return "";
        }
        return FileUtil.readString(logFile, StandardCharsets.UTF_8);
    }

}
