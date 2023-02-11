package cn.lliiooll.autotask.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import cn.lliiooll.autotask.data.bean.VaptchaBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class VaptchaService {

    @Value("${autotask.captcha.vaptcha.vid}")
    private String clientId;
    @Value("${autotask.captcha.vaptcha.key}")
    private String clientSecret;

    public boolean verify(String token, String server, String ipAddr) {
        if (!StrUtil.isAllNotBlank(token, server)) {
            return false;
        }
        VaptchaBean resp = JSONUtil.toBean(HttpRequest
                        .post(server)
                        .body(JSONUtil
                                .toJsonStr(new HashMap<String, Object>() {{
                                    put("id", clientId);
                                    put("secretkey", clientSecret);
                                    put("token", token);
                                    put("ip", ipAddr);
                                    put("scene", 0);
                                }}))
                        .contentType("application/json")
                        .execute()
                        .body(),
                VaptchaBean.class);
        return resp.getSuccess() == 1 && resp.getScore() >= 60;

    }
}
