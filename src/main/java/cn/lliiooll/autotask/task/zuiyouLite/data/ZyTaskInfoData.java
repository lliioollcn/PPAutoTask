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
public class ZyTaskInfoData {

    private List<ZySubTaskInfoData> sub_task_list;
    private String task_id;
    private String cur_task_id;
    private String task_name;
    private String task_desc;
    private String task_icon_url;
    private String task_btn_text;
    private String invoke_app_url;
    private int invoke_app_time;
    private String subtask_tobe_completed;
    private int task_hierarchy;
    private int task_interaction_type;
    private int cur_daily_task_max_inventory;
    private int cur_daily_task_depleted_sock;
    private int cur_daily_task_status;
    private ZyTaskAdInfoData task_interaction_ad;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ZySubTaskInfoData {

        private String task_id;
        private String cur_task_id;
        private String task_btn_text;
        private int task_hierarchy;
        private int task_interaction_type;
        private int cur_daily_task_max_inventory;
        private int cur_daily_task_status;
        private int sub_task_status;
        private ZyTaskAdInfoData task_interaction_ad;


    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ZyTaskAdInfoData {

        private int ad_type;
        private String ad_slot_id;


    }
}
