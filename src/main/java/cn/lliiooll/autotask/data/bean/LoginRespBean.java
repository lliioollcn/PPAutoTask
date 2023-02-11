package cn.lliiooll.autotask.data.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRespBean {
    private String token;
    private String msg;
    private int banned;
    private int authed;
}
