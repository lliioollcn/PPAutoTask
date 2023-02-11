package cn.lliiooll.autotask.data.redis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserToken {

    private String ip;// ip地址,用于检测异地登录什么的
    private String mid;// 用户mid
    private String type;// 用户访问类型,浏览器为web,安卓软件为android
    private long time;// 登录时间,用于超时自动退出登录
}
