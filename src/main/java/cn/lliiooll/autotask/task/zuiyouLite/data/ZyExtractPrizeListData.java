package cn.lliiooll.autotask.task.zuiyouLite.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZyExtractPrizeListData {


    private String prize_id;
    private String cur_prize_id;
    private String prize_name;
    private String prize_name_icon_url;
    private String prize_img_url;
    private String prize_subscript;
    private int prize_require_interact_level;
    private int prize_cnt;
    private int prize_type;
    private int cur_prize_status;


}
