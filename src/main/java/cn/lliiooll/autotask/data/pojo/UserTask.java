package cn.lliiooll.autotask.data.pojo;


import cn.hutool.json.JSONUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserTask {

    private String mid;
    private int taskType;
    private int taskStatus;
    private int id;
    private long lastTime;
    private long createTime;
    private String cookie;
    private String account;

    @Override
    public String toString() {
        return JSONUtil.toJsonStr(this);
    }
}
