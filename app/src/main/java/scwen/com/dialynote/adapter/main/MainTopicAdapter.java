package scwen.com.dialynote.adapter.main;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import scwen.com.dialynote.R;
import scwen.com.dialynote.domain.TopicBean;
import scwen.com.dialynote.domain.TopicMultiItem;
import scwen.com.dialynote.weight.TopicContainrView;

/**
 * Created by scwen on 2018/7/27.
 * QQ ：811733738
 * 作用：
 */

public class MainTopicAdapter extends BaseMultiItemQuickAdapter<TopicMultiItem, BaseViewHolder> {


    public MainTopicAdapter(List<TopicMultiItem> data) {
        super(data);
        addItemType(TopicMultiItem.ITEM_TOPIC, R.layout.item_main_topic);
    }

    @Override
    protected void convert(BaseViewHolder helper, TopicMultiItem item) {
        TopicBean topicBean = (TopicBean) item.getT();

        TopicContainrView topicContainrView = helper.getView(R.id.container_topic);

        topicContainrView.bindCommonData("张三", "", "",

                "1970-20-13 11：70", "测试", "", "");

        topicContainrView
                .bindClick("", "", "")
                .bindTitle("测试标题")
                .bindContent(topicBean.getContent())
                .bindLocation("", "")
                .bindImageOrVideo("", "1", topicBean.getImages(), topicBean.getVideos())
                .bindLike("", "", "");

    }
}
