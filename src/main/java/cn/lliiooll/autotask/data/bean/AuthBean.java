package cn.lliiooll.autotask.data.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthBean {
    private String email;
    private String token;
    private String server;
    private String passWord;
}
