package cn.lliiooll.autotask.service;

import cn.lliiooll.autotask.data.bean.AuthBean;
import cn.lliiooll.autotask.data.bean.LoginRespBean;
import cn.lliiooll.autotask.data.service.UserService;
import cn.lliiooll.autotask.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthService {

    private final RedisUtil redisUtil;
    private final UserService userService;


    @Autowired
    public AuthService(RedisUtil redisUtil, UserService userService) {
        this.redisUtil = redisUtil;
        this.userService = userService;
    }


    public boolean auth(String token, String ipAddr) {
        log.info("尝试验证用户,Token: {},IP: {}", token, ipAddr);
        return false;
    }

    public boolean register(AuthBean bean) {
        return true;
    }

    /**
     * vaptcha后端验证
     *
     * @param token
     * @param server
     * @param ipAddr
     * @return
     */
    public boolean vaptcha(String token, String server, String ipAddr) {
        return true;
    }

    public LoginRespBean login(AuthBean bean) {
        return null;
    }
}
