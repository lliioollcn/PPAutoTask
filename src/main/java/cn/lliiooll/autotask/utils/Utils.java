package cn.lliiooll.autotask.utils;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
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
        return !isNotValidBody(jstr, beanClass);
    }

    private static boolean isValidBean(String jstr, Class<?> beanClass) {
        try {
            JSONUtil.toBean(jstr, beanClass);
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    private static final File dir = new File("config");
    private static final List<File> files = new ArrayList<>() {{
        add(new File(dir, "application.properties"));
        add(new File(dir, "verifyMail.html"));
    }};


    public static void init() {
        try {
            if (!dir.exists()) {
                if (!dir.mkdirs()) {
                    throw new IOException();
                }
            }
            for (File f : files) {
                if (!f.exists()) {
                    if (!f.createNewFile()) {
                        throw new IOException();
                    } else {
                        IoUtil.copy(Utils.class.getClassLoader().getResourceAsStream(f.getName()), new FileOutputStream(f));
                    }
                }
            }
        } catch (IOException e) {
            log.error("配置文件加载失败，可能没有磁盘写入权限或磁盘已满", e);
            System.exit(0);
        }

    }
}
