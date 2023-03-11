package cn.lliiooll.autotask.data.bean;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpenUserTaskBean {

    private String mid;
    private int taskType;
    private int taskStatus;
    private int id;
    private long lastTime;
    private String taskName;
    private String account;
    private String log;
}
