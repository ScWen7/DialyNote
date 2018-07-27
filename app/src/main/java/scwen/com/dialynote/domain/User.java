package scwen.com.dialynote.domain;

import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by scwen on 2018/7/27.
 * QQ ：811733738
 * 作用：
 */

public class User extends LitePalSupport {

    private String userId;

    private String userName;

    private String avater;

    private List<TopicBean> topics;


    public String getUserId() {
        return userId == null ? "" : userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName == null ? "" : userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvater() {
        return avater == null ? "" : avater;
    }

    public void setAvater(String avater) {
        this.avater = avater;
    }

    public List<TopicBean> getTopics() {
        if (topics == null) {
            return new ArrayList<>();
        }
        return topics;
    }

    public void setTopics(List<TopicBean> topics) {
        this.topics = topics;
    }
}
