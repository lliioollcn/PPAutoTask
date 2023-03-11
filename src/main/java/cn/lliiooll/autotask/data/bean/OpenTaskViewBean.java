package cn.lliiooll.autotask.data.bean;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpenTaskViewBean {

    private String name;
    private String email;
    private long last;
    private String task;
    private int status;
}
