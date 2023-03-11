package cn.lliiooll.autotask.data.bean;


import cn.hutool.json.JSONUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserTaskBean {

    private String mid;
    private int taskType;
    private int taskStatus;
    private int id;
    private long lastTime;
    private String cookie;
    private String taskName;
    private String account;
}
