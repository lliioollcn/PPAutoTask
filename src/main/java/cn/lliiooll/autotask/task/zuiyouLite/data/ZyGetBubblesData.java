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
public class ZyGetBubblesData {

    private List<ZyGetBubblesDataItem> list;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ZyGetBubblesDataItem {

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
