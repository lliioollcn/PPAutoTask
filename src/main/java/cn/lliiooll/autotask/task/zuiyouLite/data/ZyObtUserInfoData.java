package cn.lliiooll.autotask.task.zuiyouLite.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZyObtUserInfoData {

    private String uid;
    private int user_credit;
    private int user_level;
    private int residue_available_credit;

}
