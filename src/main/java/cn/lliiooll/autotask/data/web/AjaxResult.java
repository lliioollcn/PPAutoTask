package cn.lliiooll.autotask.data.web;

import cn.hutool.json.JSONUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AjaxResult {
    private int status;
    private String msg;
    private Object data;

    @Override
    public String toString()  {
        return JSONUtil.toJsonStr(this);
    }
}
