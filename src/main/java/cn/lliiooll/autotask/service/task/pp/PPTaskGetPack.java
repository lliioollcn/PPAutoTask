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
public class PPTaskGetPack extends PPTaskBase {
    private PPService ppService;

    @Autowired
    public PPTaskGetPack(PPService ppService) {
        this.ppService = ppService;
    }

    @PostConstruct
    public void init() {
        ppService.register(this);
    }

    @Override
    public boolean doTask(String token, FileWriter writer) throws Throwable {
        writer.write("开始获取背包物品\n");
        log.info("开始获取背包物品\n");
        JSONObject pd = JSONUtil.parseObj(token);
        pd.set("pack_tab", 1);
        String result = HttpUtil.createPost("https://h5.ippzone.com/ppapi/lottery/get_pack_items_by_tab").body(pd.toString()).execute().body();
        if (JSONUtil.isTypeJSON(result)) {
            JSONObject json = JSONUtil.parseObj(result);
            JSONArray list = json.getJSONObject("data").getJSONArray("list");
            writer.write("开始遍历物品\n");
            log.info("开始遍历物品\n");
            for (JSONObject item : list.jsonIter()) {
                String name = item.getJSONObject("prize_kind").getStr("name");
                if (StrUtil.isNotBlank(name) && name.equalsIgnoreCase("刮刮乐")) {
                    JSONObject extra = item.getJSONObject("extra");
                    writer.write("开始打开刮刮乐: " + extra.getStr("id") + "\n");
                    log.info("开始打开刮刮乐: " + extra.getStr("id") + "\n");
                    JSONObject pushData = JSONUtil.parseObj(token);
                    pushData.putAll(extra);
                    pushData.set("receive_from", "" + extra.getInt("receive_from"));
                    pushData.set("pack_id", item.getStr("id"));
                    String r1 = HttpUtil.createPost("https://h5.ippzone.com/ppapi/treasure_hunt/open_box_v2").body(pushData.toString()).execute().body();
                    log.info(r1);
                    if (JSONUtil.isTypeJSON(r1)) {
                        writer.write("打开成功: " + extra.getStr("id") + "\n");
                        log.info("打开成功: " + extra.getStr("id") + "\n");
                    } else {
                        writer.write("打开成功: 不是有效的json\n");
                        log.info("打开成功: 不是有效的json\n");
                    }
                }
            }
        } else {
            writer.write("任务失败: 不是有效的json\n");
            log.info("任务失败: 不是有效的json\n");
        }
        return false;
    }
}
