package cn.lliiooll.autotask.data.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IPInfoBean {

    private String msg;
    private int code;
    private IPInfoDataBean data;


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class IPInfoDataBean {

        private String country;
        private String area;
        private String city;
        private String region;
        private String region_id;
        private String city_id;
        private String country_id;


    }
}
