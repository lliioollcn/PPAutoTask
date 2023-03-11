package cn.lliiooll.autotask.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NetUtils {
    static final String UNKNOWN = "unknown";

    public static String getIpAddr(HttpServletRequest request) {
        if (request == null) {
            return UNKNOWN;
        }
        String ip = request.getHeader("X-Forwarded-For");
        //log.info("获取ip1: {}", ip);
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        //log.info("获取ip2: {}", ip);
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
       // log.info("获取ip3: {}", ip);
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        //log.info("获取ip4: {}", ip);
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
       // log.info("获取ip5: {}", ip);
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
       // log.info("获取ip6: {}", ip);
        if (ip.contains(",")){
            ip = ip.split(",")[0];
        }
        log.info("获取ip7: {}", ip);
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }
}
