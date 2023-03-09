package cn.lliiooll.autotask.service;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.DES;
import cn.lliiooll.autotask.data.bean.AuthBean;
import cn.lliiooll.autotask.data.bean.LoginRespBean;
import cn.lliiooll.autotask.data.pojo.UserData;
import cn.lliiooll.autotask.data.redis.UserToken;
import cn.lliiooll.autotask.data.service.UserService;
import cn.lliiooll.autotask.utils.RedisUtil;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@AllArgsConstructor
public class AuthService {
    private final RedisUtil redisUtil;
    private final UserService userService;
    private final MailService mailService;
    private final VaptchaService vaptchaService;
    private final Oauth2Service oauth2Service;
    private final SecurityService securityService;

    @PostConstruct
    public void init() {
        /*
        register(AuthBean.builder()
                .email("lliiooll@lliiooll.cn")
                .passWord("18169081668qq")
                .build());

         */
    }

    public boolean auth(String token, String ipAddr) {
        log.info("尝试验证用户,Token: {},IP: {}", token, ipAddr);
        String[] tokenType = token.split("\\_");
        if (tokenType.length == 3) {
            String timeStr = tokenType[2];
            if (StrUtil.isNumeric(timeStr)) {
                long time = Long.parseLong(timeStr);
                String key = tokenType[0] + "_" + tokenType[1];
                Object ins = redisUtil.get(key);
                if (ins instanceof UserToken) {
                    UserToken uT = (UserToken) ins;
                    if (uT.getTime() == time) {
                        if (uT.getIp().equalsIgnoreCase(ipAddr)) {
                            return true;
                        } else {
                            return securityService.isSafe(ipAddr, uT.getIp());
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean register(AuthBean bean) {
        if (!userService.checkUserByEmail(bean.getEmail())) {
            String mid = RandomUtil.randomString(RandomUtil.BASE_CHAR.toLowerCase() +
                    RandomUtil.BASE_CHAR.toUpperCase() +
                    RandomUtil.BASE_NUMBER, 5);
            if (!userService.checkUserByMid(mid)) {
                String salt = RandomUtil.randomString(16);
                DES des = SecureUtil.des();
                String pass = SecureUtil.sha1(des.encryptHex(bean.getPassWord()) + "@" + SecureUtil.md5(salt)) + "#" + Base64.encode(des.getSecretKey().getEncoded()) + "#" + salt;
                userService.insertUserData(UserData.builder()
                        .mid(mid)
                        .email(bean.getEmail())
                        .emailAuthed(0)
                        .passWord(pass)
                        .isAdmin(0)
                        .isBanned(0)
                        .reason("")
                        .userName(bean.getEmail())
                        .build());
                //mailService.sendVerifyMail(bean.getEmail());
                return true;
            } else {
                register(bean);
            }
        }
        return false;
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
        return vaptchaService.verify(token, server, ipAddr);
    }

    public LoginRespBean login(AuthBean bean, String ipAddr) {
        UserData data = userService.selectUserDataByEmail(bean.getEmail());
        if (data != null) {
            String[] passType = data.getPassWord().split("#");
            if (passType.length == 3) {
                DES des = SecureUtil.des(Base64.decode(passType[1]));
                String salt = passType[2];
                String pass = SecureUtil.sha1(des.encryptHex(bean.getPassWord()) + "@" + SecureUtil.md5(salt));
                if (pass.equalsIgnoreCase(passType[0])) {
                    String token = "at_" + SecureUtil.hmacSha1(data.getMid()).digestHex(data.getEmail());
                    Object ins = redisUtil.get(token);
                    if (ins instanceof UserToken) {
                        UserToken uT = (UserToken) redisUtil.get(token);
                        if (!ipAddr.equalsIgnoreCase(uT.getIp())) {
                            if (!securityService.isSafe(ipAddr, uT.getIp())) {
                                return LoginRespBean.builder()
                                        .authed(-1)
                                        .banned(-1)
                                        .msg("用户已经在线")
                                        .build();
                            }
                        }
                    }
                    long time = System.currentTimeMillis();
                    redisUtil.set(token, UserToken.builder()
                            .ip(ipAddr)
                            .time(time)
                            .mid(data.getMid())
                            .type("none")
                            .build(), 7200);
                    return LoginRespBean.builder()
                            .authed(data.getEmailAuthed())
                            .banned(data.getIsBanned())
                            .msg("登录成功")
                            .token(token + "_" + time)
                            .build();
                }
            }
        }
        return null;
    }

    public boolean verify(String code) {
        Object ins = redisUtil.get("mail_" + code);
        if (ins == null) {
            return false;
        }
        String email = (String) ins;
        UserData data = userService.selectUserDataByEmail(email);
        if (data == null) {
            return false;
        }
        data.setEmailAuthed(1);
        userService.updateUserData(data);
        return true;
    }
}
