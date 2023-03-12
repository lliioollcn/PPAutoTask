package cn.lliiooll.autotask.task.zuiyouLite.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZyGetRewardData {

    private ZyGetBubblesDataBubble bubble;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ZyGetBubblesDataBubble {
        private int status;
        private int count_down;
        private int award_cnt;
        private int receive_from;
        private int type;
        private int task_point;
        private String award_name;
        private String icon;
        private String award_icon;
        private String router;
        private String pack_id;
        private String id;
        private String task_name;

    }
}
