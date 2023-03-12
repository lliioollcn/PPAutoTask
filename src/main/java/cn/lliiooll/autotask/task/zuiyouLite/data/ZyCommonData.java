package cn.lliiooll.autotask.task.zuiyouLite.data;

import cn.hutool.json.JSONUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZyCommonData<T> {

    private T data;
    private int ret;// -1是失败,1是成功
    private String msg;

    @Override
    public String toString() {
        return JSONUtil.toJsonStr(this);
    }


    public String toPrettyString() {
        return JSONUtil.toJsonPrettyStr(this);
    }
}
