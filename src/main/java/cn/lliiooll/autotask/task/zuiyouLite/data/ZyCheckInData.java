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
public class ZyCheckInData {

    private List<ZyCheckInDataItem> list;
    private String from;
    private String order_id;
    private String msg;
    private int continues_days;
    private int box_type;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ZyCheckInDataItem {

        private int count;
        private int is_frag;
        private int kind_id;
        private int pack_frag_count;
        private int convert_count;
        private int receive_from;
        private String name;
        private String id;
        private String unit;
        private String pack_id;
        private String desc;
        private String url;

    }
}
