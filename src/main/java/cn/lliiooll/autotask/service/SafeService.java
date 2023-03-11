package cn.lliiooll.autotask.service;

import cn.lliiooll.autotask.utils.NetUtils;
import cn.lliiooll.autotask.utils.RedisUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SafeService {

    private RedisUtil redis;

    @Autowired
    public SafeService(RedisUtil redis) {
        this.redis = redis;
    }

    public boolean limit(HttpServletRequest request, String email) {
        String ip = NetUtils.getIpAddr(request);
        Object ipLo = redis.get(ip);
        Object emLo = redis.get(email);
        if (ipLo == null || emLo == null) {
            redis.set(ip, "1", 60 * 10);
            redis.set(email, "1", 60 * 10);
            return false;
        }
        String ipL = (String) ipLo;
        String emL = (String) emLo;
        int ipC = Integer.parseInt(ipL);
        int emC = Integer.parseInt(emL);
        if (ipC > 300 || emC > 300) {
            redis.set(ip, ipC, 60 * 10);
            redis.set(email, emC, 60 * 10);
            return true;
        }
        ipC++;
        emC++;
        redis.set(ip, ipC, 60 * 10);
        redis.set(email, emC, 60 * 10);
        return false;
    }
}
