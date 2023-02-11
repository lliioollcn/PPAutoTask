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
public class SysTask {

    private String taskName;
    private int taskType;

    @Override
    public String toString() {
        return JSONUtil.toJsonStr(this);
    }
}
