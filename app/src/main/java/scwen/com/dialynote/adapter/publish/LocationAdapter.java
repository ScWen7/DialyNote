package scwen.com.dialynote.adapter.publish;

import android.support.annotation.Nullable;

import com.amap.api.services.core.PoiItem;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import scwen.com.dialynote.R;

/**
 * Created by xxh on 2018/6/7.
 */

public class LocationAdapter extends BaseQuickAdapter<PoiItem, BaseViewHolder> {

    private int checkIndex = -1;

    public void setCheckIndex(int checkIndex) {
        this.checkIndex = checkIndex;
    }

    public LocationAdapter(@Nullable List<PoiItem> data) {
        super(R.layout.item_choose_location, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PoiItem item) {

        int adapterPosition = helper.getLayoutPosition() - getHeaderLayoutCount();
        helper.setGone(R.id.iv_tick, adapterPosition == checkIndex);

        helper.setText(R.id.tv_location, item.getTitle())
                .setText(R.id.tv_location_detail, item.getSnippet());


    }
}
