package cn.lliiooll.autotask.data.pojo;


import cn.hutool.json.JSONUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserData {

    private String mid;
    private String userName;
    private String email;
    private String passWord;
    private String reason;
    private int emailAuthed;
    private long createTime;
    private int isAdmin;
    private int isBanned;

    @Override
    public String toString() {
        return JSONUtil.toJsonStr(this);
    }
}
