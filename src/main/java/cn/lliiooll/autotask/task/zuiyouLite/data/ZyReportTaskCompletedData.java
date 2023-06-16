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
public class ZyReportTaskCompletedData {

    private List<ZyReportTaskCompletedRewardList> reward_list;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ZyReportTaskCompletedRewardList {
        private int reward_points;

    }
}
