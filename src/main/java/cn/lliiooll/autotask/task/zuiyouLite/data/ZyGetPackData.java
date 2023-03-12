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
public class ZyGetPackData {

    private List<ZyGetPackDataItem> list;
    private int more;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ZyGetPackDataItem {

        private int count;
        private int valid_seconds;
        private long valid_t;
        private long receive_t;
        private long mid;
        private long expired_t;
        private int kind_id;
        private int kind_type;
        private int section;
        private int inuse;
        private int status;
        private String valid_text;
        private String id;
        private ZyGetPackDataItemKind prize_kind;
        private ZyGetPackDataItemExtra extra;


        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class ZyGetPackDataItemExtra {
            private int receive_from;
            private String id;
        }

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class ZyGetPackDataItemKind {

            private int id;
            private int parent_type;
            private int section;
            private int parent_id;
            private int pack_tab;
            private int type;
            private int status;
            private int price;
            private int pack_order;
            private int valid_seconds;
            private int compound_value;
            private long ct;
            private long ut;
            private String icon;
            private String pack_url_out;
            private String icon_android;
            private String name;
            private String url;
            private String night_icon_out;
            private String color;
            private String valid_text;
            private String pack_url;
            private String icon_out;
            private String unit;
            private String pack_bg_out;
            private String night_icon;
            private String bubble_router;
            private String pack_bg;
            private String desc;

        }
    }
}
