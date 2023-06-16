package cn.lliiooll.autotask.task.zuiyouLite.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZyEntranceData {


    private String icon;
    private Object gift;
    private Object process;
    private int retained;
    private int is_selected;
    private int user_state;
    private int open;


}
