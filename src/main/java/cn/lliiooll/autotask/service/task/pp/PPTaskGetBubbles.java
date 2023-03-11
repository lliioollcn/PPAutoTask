package cn.lliiooll.autotask.service.task.pp;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.lliiooll.autotask.service.task.PPService;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileWriter;

@Slf4j
@Component
public class PPTaskGetBubbles extends PPTaskBase {
    private PPService ppService;

    @Autowired
    public PPTaskGetBubbles(PPService ppService) {
        this.ppService = ppService;
    }

    @PostConstruct
    public void init() {
        ppService.register(this);
    }


    @Override
    public boolean doTask(String token, FileWriter writer)throws Throwable {
        writer.write("开始获取馒头奖励\n");
        log.info("开始获取馒头奖励\n");
        String result = HttpUtil.createPost("https://h5.ippzone.com/ppapi/treasure_hunt/get_bubbles").body(token).execute().body();
        if (JSONUtil.isTypeJSON(result)) {
            JSONObject json = JSONUtil.parseObj(result);
            JSONArray list = json.getJSONObject("data").getJSONArray("list");
            writer.write("开始领取馒头奖励\n");
            log.info("开始领取馒头奖励\n");
            for (JSONObject bubble : list.jsonIter()) {
                String id = bubble.getStr("id");
                if (StrUtil.isNotBlank(id)) {
                    writer.write("领取馒头奖励,id: " + id + "\n");
                    log.info("领取馒头奖励,id: " + id + "\n");
                    JSONObject pushData = JSONUtil.parseObj(token);
                    pushData.putAll(bubble);
                    String r1 = HttpUtil.createPost("https://h5.ippzone.com/ppapi/treasure_hunt/click").body(pushData.toString()).execute().body();
                    if (JSONUtil.isTypeJSON(r1)) {
                        writer.write("领取成功: " + id + "\n");
                        log.info("领取成功: " + id + "\n");
                    } else {
                        writer.write("领取失败: 不是有效的json\n");
                        log.info("领取失败: 不是有效的json\n");
                    }
                }
            }
        } else
            writer.write("任务失败: 不是有效的json\n");
        log.info("任务失败: 不是有效的json\n");
        return false;
    }
}
