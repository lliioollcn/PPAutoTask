package cn.lliiooll.autotask.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;

public class Utils {
    public static boolean isNotValidBody(String jstr) {
        return !isValidBody(jstr);
    }

    public static boolean isValidBody(String jstr) {
        return StrUtil.isNotBlank(jstr) && JSONUtil.isTypeJSON(jstr);
    }

    public static boolean isNotValidBody(String jstr, Class<?> beanClass) {
        return isNotValidBody(jstr) || !isValidBean(jstr, beanClass);
    }

    public static boolean isValidBody(String jstr, Class<?> beanClass) {
        return !isNotValidBody(jstr,beanClass);
    }

    private static boolean isValidBean(String jstr, Class<?> beanClass) {
        try {
            JSONUtil.toBean(jstr, beanClass);
            return true;
        } catch (Throwable e) {
            return false;
        }
    }
}
