package cn.lliiooll.autotask.task.zuiyouLite.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZyObtGameData {

    private String interact_play_id;
    private String interact_play_name;
    private String interact_level_title_icon_url;
    private String interact_level_img_url;
    private int interact_level;
    private int prize_cost;
    private boolean is_unlock;

}
