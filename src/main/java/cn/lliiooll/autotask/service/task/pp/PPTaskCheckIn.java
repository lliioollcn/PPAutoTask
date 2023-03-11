package cn.lliiooll.autotask.service.task.pp;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.lliiooll.autotask.service.task.PPService;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileWriter;

@Slf4j
@Component
public class PPTaskCheckIn extends PPTaskBase {
    private PPService ppService;

    @Autowired
    public PPTaskCheckIn(PPService ppService) {
        this.ppService = ppService;
    }

    @PostConstruct
    public void init() {
        ppService.register(this);
    }


    @Override
    public boolean doTask(String token, FileWriter writer) throws Throwable{
        writer.write("开始签到\n");
        log.info("开始签到\n");
        String result = HttpUtil.createPost("https://h5.ippzone.com/ppapi/treasure_hunt/check_in").body(token).execute().body();
        if (JSONUtil.isTypeJSON(result)) {
            JSONObject json = JSONUtil.parseObj(result);
            if (json.getJSONObject("data") != null) {
                JSONArray list = json.getJSONObject("data").getJSONArray("list");
                writer.write("开始遍历物品\n");
                log.info("开始遍历物品\n");
                for (JSONObject item : list.jsonIter()) {
                    String name = item.getStr("name");
                    if (StrUtil.isNotBlank(name) && name.equalsIgnoreCase("刮刮乐")) {
                        String id = item.getStr("id");
                        String pack_id = item.getStr("pack_id");
                        String receive_from = item.getInt("receive_from") + "";
                        writer.write("开始打开刮刮乐: " + id + "\n");
                        log.info("开始打开刮刮乐: " + id + "\n");
                        JSONObject pushData = JSONUtil.parseObj(token);
                        pushData.set("receive_from", receive_from);
                        pushData.set("pack_id", pack_id);
                        String r1 = HttpUtil.createPost("https://h5.ippzone.com/ppapi/treasure_hunt/open_box_v2").body(pushData.toString()).execute().body();
                        if (JSONUtil.isTypeJSON(r1)) {
                            writer.write("打开成功: " + id + "\n");
                            log.info("打开成功: " + id + "\n");
                        } else {
                            writer.write("打开失败: 不是有效的json\n");
                            log.info("打开失败: 不是有效的json\n");
                        }
                    }
                }
            }

        } else
            writer.write("任务失败: 不是有效的json\n");
        log.info("任务失败: 不是有效的json\n");
        return false;
    }
}
