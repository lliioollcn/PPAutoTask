package cn.lliiooll.autotask.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import cn.lliiooll.autotask.data.bean.VaptchaBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Slf4j
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
        log.info("尝试验证手势验证码,token: {},server: {},ip: {}", token, server, ipAddr);
        log.info("请求数据: {}", JSONUtil
                .toJsonStr(new HashMap<String, Object>() {{
                    put("id", clientId);
                    put("secretkey", clientSecret);
                    put("token", token);
                    put("ip", ipAddr);
                    put("scene", 0);
                }}));
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
        log.info("验证结果: success:{},score:{},msg:{}", resp.getSuccess(), resp.getScore(), resp.getMsg());
        return resp.getSuccess() == 1 && resp.getScore() >= 60;

    }
}
