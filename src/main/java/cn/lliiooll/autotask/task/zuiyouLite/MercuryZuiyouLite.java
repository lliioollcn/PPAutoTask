package cn.lliiooll.autotask.task.zuiyouLite;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.lliiooll.autotask.task.zuiyouLite.data.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
public class MercuryZuiyouLite {

    private final JSONObject token;

    public MercuryZuiyouLite(String token) throws Throwable {
        if (JSONUtil.isTypeJSON(token)) {
            JSONObject old = JSONUtil.parseObj(token);
            this.token = JSONUtil.createObj(JSONConfig.create().setIgnoreNullValue(false).setStripTrailingZeros(false));
            String h_did = old.getStr("h_did");
            long h_m = old.getLong("h_m");
            this.token.set("did", h_did);
            this.token.set("h_did", h_did);
            this.token.set("uid", String.valueOf(h_m));
            this.token.set("h_nt", 4);
            this.token.set("sdk_ver", "3.0.22.0");
            this.token.set("appid", "10028");
            this.token.set("h_os", String.valueOf(old.get("h_os")));
            this.token.set("h_model", old.get("h_model"));
            this.token.set("h_dt", old.get("h_dt"));
            this.token.set("h_carrier", "中国移动");
            this.token.set("interact_play_id", "1");
            this.token.set("name_space", "pipi_car");
            this.token.set("manufacturer", "OPPO");
            this.token.set("resolution", "1080x2200");
            this.token.set("h_mac", "02:00:00:00:00:00");
            this.token.set("h_ids", new HashMap<>() {{
                put("android_id", h_did);
            }});
            this.token.set("ext", new HashMap<>() {{
                put("mid", String.valueOf(h_m));
                put("did", h_did);
                put("app_ver", old.get("h_pipi"));
            }});
        } else {
            throw new RuntimeException("错误的token");
        }
    }

    public static MercuryZuiyouLite newMercury(String token) throws Throwable {
        return new MercuryZuiyouLite(token);
    }

    public ZyCommonData<List<ZyExtractPrizeListData>> getExtractPrizeList() throws Throwable {
        if (this.token != null) {
            token.set("ts", System.currentTimeMillis());
        }
        return dataOrNullArray(ZyApis.EXTRACT_PRIZE_LIST, ZyExtractPrizeListData.class, token);
    }

    public ZyCommonData<ZyObtGameData> obtainGameplayAttributes() throws Throwable {
        if (this.token != null) {
            token.set("ts", System.currentTimeMillis());
        }
        return dataOrNull(ZyApis.OBTAIN_GAME_PLAY_ATTRIBUTES, ZyObtGameData.class, token);
    }

    public ZyCommonData<ZyReportTaskCompletedData> reportTaskCompleted(int task_interaction_type, String task_id, String cur_task_id, int task_hierarchy) throws Throwable {
        if (this.token != null) {
            token.set("ts", System.currentTimeMillis());
        }
        JSONObject data = JSONUtil.createObj();
        data.putAll(this.token);
        data.set("extra", new HashMap<>() {{
            put("reward_cnt", 1);
        }});
        data.set("task_interaction_type", task_interaction_type);
        data.set("task_id", task_id);
        data.set("cur_task_id", cur_task_id);
        data.set("task_hierarchy", task_hierarchy);
        return dataOrNull(ZyApis.REPORT_TASK_COMPLETED, ZyReportTaskCompletedData.class, data);
    }

    public ZyCommonData<List<Object>> obtainRedeemPrizeRecords() throws Throwable {
        if (this.token != null) {
            token.set("ts", System.currentTimeMillis());
        }

        return dataOrNullArray(ZyApis.OBTAIN_REDEEM_PRIZE_RECORDS, Object.class, token);
    }

    public ZyCommonData<ZyObtUserInfoData> obtainUserInfo() throws Throwable {
        if (this.token != null) {
            token.set("ts", System.currentTimeMillis());
        }
        JSONObject data = JSONUtil.createObj();
        data.putAll(this.token);
        data.put("applist", new ArrayList<>() {{
            add("com.quark.browser");
            add("com.UCMobile");
        }});
        return dataOrNull(ZyApis.OBTAIN_USER_INFO, ZyObtUserInfoData.class, data);
    }

    public ZyCommonData<List<ZyTaskInfoData>> getTaskList() throws Throwable {
        if (this.token != null) {
            token.set("ts", System.currentTimeMillis());
        }
        JSONObject data = JSONUtil.createObj();
        data.putAll(this.token);
        data.put("applist", new ArrayList<>() {{
            add("com.quark.browser");
            add("com.UCMobile");
        }});
        return dataOrNullArray(ZyApis.GET_TASK_LIST, ZyTaskInfoData.class, data);
    }

    private <T> ZyCommonData<T> dataOrNull(String url, Class<T> type, JSONObject body) throws Throwable {
        if (this.token != null) {
            token.set("ts", System.currentTimeMillis());
        }
        //  log.info("body({}): {}", url, body.toStringPretty());
        String jstr = HttpUtil.createPost(ZyApis.API_HOST + url).body(body.toString()).contentType("application/json").execute().body();
        // log.info("result({}): {}", url, JSONUtil.formatJsonStr(jstr));
        if (JSONUtil.isTypeJSON(jstr)) {
            JSONObject json = JSONUtil.parseObj(jstr);
            ZyCommonData<T> result = new ZyCommonData<T>();
            T data = JSONUtil.toBean(json.getJSONObject("data"), type);
            result.setData(data);
            return result;
        }

        return null;
    }

    private <T> ZyCommonData<List<T>> dataOrNullArray(String url, Class<T> type, JSONObject body) throws Throwable {
        if (this.token != null) {
            token.set("ts", System.currentTimeMillis());
        }
        //log.info("body({}): {}", url, body.toStringPretty());
        String jstr = HttpUtil.createPost(ZyApis.API_HOST + url).body(body.toString()).contentType("application/json").execute().body();
        //log.info("result({}): {}", url, JSONUtil.formatJsonStr(jstr));
        if (JSONUtil.isTypeJSON(jstr)) {
            JSONObject json = JSONUtil.parseObj(jstr);
            ZyCommonData<List<T>> result = new ZyCommonData<List<T>>();
            JSONArray datas = json.getJSONArray("data");
            List<T> data = new ArrayList<>();
            datas.forEach(d -> {
                if (d instanceof JSONObject) {
                    data.add(JSONUtil.toBean((JSONObject) d, type));
                }
            });
            result.setData(data);
            return result;
        }

        return null;
    }

    class ZyApis {

        protected static final String API_HOST = "https://mercuryh5.ixiaochuan.cn";
        // 获取奖励列表
        protected static final String EXTRACT_PRIZE_LIST = "/api/plutus/gameplay/extract_prize_list";
        // 未知操作
        protected static final String INNO_ACTION = "/api/stat/inno_action";
        // 获取奖励（可能）
        protected static final String OBTAIN_GAME_PLAY_ATTRIBUTES = "/api/plutus/gameplay/obtain_gameplay_attributes";
        // 获取奖励（可能）
        protected static final String OBTAIN_REDEEM_PRIZE_RECORDS = "/api/plutus/gameplay/obtain_redeem_prize_records";
        // 完成任务
        protected static final String REPORT_TASK_COMPLETED = "/api/plutus/gameplay/report_task_completed";
        // 任务列表
        protected static final String GET_TASK_LIST = "/api/plutus/gameplay/get_task_list";
        // 用户信息
        protected static final String OBTAIN_USER_INFO = "/api/plutus/gameplay/obtain_user_info";
    }

}
