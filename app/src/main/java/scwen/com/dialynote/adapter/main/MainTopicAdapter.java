package scwen.com.dialynote.adapter.main;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import scwen.com.dialynote.R;
import scwen.com.dialynote.domain.TopicBean;
import scwen.com.dialynote.domain.TopicMultiItem;

/**
 * Created by scwen on 2018/7/27.
 * QQ ：811733738
 * 作用：
 */

public class MainTopicAdapter extends BaseMultiItemQuickAdapter<TopicMultiItem, BaseViewHolder> {


    public MainTopicAdapter(List<TopicMultiItem> data) {
        super(data);
//        addItemType(TopicMultiItem.ITEM_TOPIC, R.layout.circle_hot_recommend);
    }

    @Override
    protected void convert(BaseViewHolder helper, TopicMultiItem item) {

    }
}
