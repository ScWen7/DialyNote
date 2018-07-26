package scwen.com.dialynote.utils;

import android.content.Context;

import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.IncapableCause;
import com.zhihu.matisse.internal.entity.Item;

import java.util.Set;

/**
 * Created by scwen on 2018/7/26.
 * QQ ：811733738
 * 作用：
 */

public class VideoDurationFilter extends Filter {


    private long minDuration;

    public VideoDurationFilter(long minDuration) {
        this.minDuration = minDuration;
    }

    @Override
    protected Set<MimeType> constraintTypes() {
        return MimeType.of(MimeType.MP4);
    }

    @Override
    public IncapableCause filter(Context context, Item item) {
        if (!needFiltering(context, item))
            return null;

        long duration = item.duration;
        if (duration < minDuration) {
            return new IncapableCause(IncapableCause.DIALOG,"视频最短"+(minDuration/1000)+"秒");
        }

        return null;
    }
}
