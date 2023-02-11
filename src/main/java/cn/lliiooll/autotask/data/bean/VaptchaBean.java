package cn.lliiooll.autotask.data.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VaptchaBean {
    private int success;
    private int score;
    private String msg;
}
