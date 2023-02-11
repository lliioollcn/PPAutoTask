package cn.lliiooll.autotask.service;

import org.springframework.stereotype.Service;

@Service
public class SecurityService {
    public boolean isSafe(String ipAddr, String ip) {
        // 感觉太tm费服务器了
        /*
        String jstr1 = HttpUtil.createPost("https://ip.taobao.com/outGetIpInfo")
                .body("ip=" + ipAddr + "&accessKey=alibaba-inc", "application/x-www-form-urlencoded")
                .execute()
                .body();
        String jstr2 = HttpUtil.createPost("https://ip.taobao.com/outGetIpInfo")
                .body("ip=" + ip + "&accessKey=alibaba-inc", "application/x-www-form-urlencoded")
                .execute()
                .body();
        if (StrUtil.isAllNotBlank(jstr2, jstr1)) {
            IPInfoBean i1 = JSONUtil.toBean(jstr1, IPInfoBean.class);
            IPInfoBean i2 = JSONUtil.toBean(jstr1, IPInfoBean.class);
            return i1.getData().getRegion_id().equalsIgnoreCase(i2.getData().getRegion_id()) && i1.getData().getCountry_id().equalsIgnoreCase(i2.getData().getCountry_id());
        }
        return false;

         */
        return true;
    }
}
