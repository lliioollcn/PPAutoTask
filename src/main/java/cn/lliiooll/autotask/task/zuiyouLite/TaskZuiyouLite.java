package cn.lliiooll.autotask.task.zuiyouLite;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.lliiooll.autotask.task.zuiyouLite.data.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TaskZuiyouLite {


    private final JSONObject token;

    public TaskZuiyouLite(String token) throws Throwable {
        if (JSONUtil.isTypeJSON(token)) {
            this.token = JSONUtil.parseObj(token);
        } else {
            throw new RuntimeException("错误的token");
        }
    }

    public static TaskZuiyouLite newTask(String token) throws Throwable {
        return new TaskZuiyouLite(token);
    }

    public ZyCommonData<ZyGetBubblesData> getBubbles() throws Throwable {
        return dataOrNull(ZyApis.GET_BUBBLES, ZyGetBubblesData.class, token);
    }

    public ZyCommonData<ZyCheckInData> checkIn() throws Throwable {
        return dataOrNull(ZyApis.CHECK_IN, ZyCheckInData.class, token);
    }

    public ZyCommonData<ZyOpenBoxV2Data> openBoxV2(ZyGetPackData.ZyGetPackDataItem item) throws Throwable {
        JSONObject body = JSONUtil.createObj();
        body.putAll(token);
        JSONObject extra = JSONUtil.parseObj(item.getExtra());
        extra.set("receive_from", item.getExtra().getReceive_from() + "");
        body.putAll(extra);
        body.set("pack_id", item.getId());
        return dataOrNull(ZyApis.OPEN_BOX_V2, ZyOpenBoxV2Data.class, body);
    }


    public ZyCommonData<ZyGetRewardData> getBubbleReward(ZyGetBubblesData.ZyGetBubblesDataItem item) throws Throwable {
        JSONObject body = JSONUtil.createObj();
        body.putAll(token);
        body.putAll(JSONUtil.parseObj(item));
        return dataOrNull(ZyApis.CLICK_REWARD, ZyGetRewardData.class, body);
    }

    public ZyCommonData<ZyGetPackData> getPackItems(int offset) throws Throwable {
        JSONObject body = JSONUtil.createObj();
        body.putAll(token);
        body.set("pack_tab", 1);
        body.set("offset", offset);
        body.set("limit", 300);
        return dataOrNull(ZyApis.GET_PACK_ITEMS, ZyGetPackData.class, body);
    }

    public ZyCommonData<ZyGetPackData> getPackItems() throws Throwable {
        return getPackItems(0);
    }

    public List<ZyGetPackData> getAllPackItems() throws Throwable {
        List<ZyGetPackData> datas = new ArrayList<>();
        int offset = 0;
        while (true) {
            ZyCommonData<ZyGetPackData> gpd = getPackItems(offset);
            if (gpd.getRet() == -1) {
                break;
            } else {
                datas.add(gpd.getData());
                if (gpd.getData().getMore() != 0) {
                    offset++;
                } else {
                    break;
                }
            }
            Thread.sleep(100L);
        }
        return datas;
    }

    private <T> ZyCommonData<T> dataOrNull(String url, Class<T> type, JSONObject body) throws Throwable {

        // log.info("body: {}", body.toStringPretty());
        String jstr = HttpUtil.createPost(ZyApis.API_HOST + url).body(body.toString()).contentType("application/json").execute().body();
        //log.info("result: {}", JSONUtil.formatJsonStr(jstr));
        if (JSONUtil.isTypeJSON(jstr)) {
            JSONObject json = JSONUtil.parseObj(jstr);
            ZyCommonData<T> result = new ZyCommonData<T>();
            T data = JSONUtil.toBean(json.getJSONObject("data"), type);
            result.setData(data);
            return result;
        }

        return null;
    }


    class ZyApis {

        protected static final String API_HOST = "https://h5.ippzone.com";
        // 获取未收获的奖励列表
        protected static final String GET_BUBBLES = "/ppapi/treasure_hunt/get_bubbles";
        // 收获奖励
        protected static final String CLICK_REWARD = "/ppapi/treasure_hunt/click";
        // 获取背包物品
        protected static final String GET_PACK_ITEMS = "/ppapi/lottery/get_pack_items_by_tab";
        // 签到
        protected static final String CHECK_IN = "/ppapi/treasure_hunt/check_in";
        // 开刮刮乐
        protected static final String OPEN_BOX_V2 = "/ppapi/treasure_hunt/open_box_v2";
        // 公益页面
        protected static final String WELFARE_PAGE = "/welfare/api/proxy?url=http://api.in.ippzone.com/welfare/get_welfare_page";
        // 捐爱心
        protected static final String CONSUME_HEART = "/welfare/api/proxy?url=http://api.in.ippzone.com/welfare/consume_love_heart";
    }

}
