package scwen.com.dialynote.domain;

import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by scwen on 2018/7/25.
 * QQ ：811733738
 * 作用：
 */

public class LabelBean extends LitePalSupport {

    private String content;

    private String labelId;


    private List<TopicBean> topics;

    public List<TopicBean> getTopics() {
        if (topics == null) {
            return new ArrayList<>();
        }
        return topics;
    }

    public void setTopics(List<TopicBean> topics) {
        this.topics = topics;
    }

    public String getLabelId() {
        return labelId == null ? "" : labelId;
    }

    public void setLabelId(String labelId) {
        this.labelId = labelId;
    }

    public String getContent() {
        return content == null ? "" : content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
