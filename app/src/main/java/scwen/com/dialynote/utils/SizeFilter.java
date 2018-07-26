package scwen.com.dialynote.utils;


import android.content.Context;
import android.graphics.Point;

import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.IncapableCause;
import com.zhihu.matisse.internal.entity.Item;
import com.zhihu.matisse.internal.utils.PhotoMetadataUtils;

import java.util.Set;

import scwen.com.dialynote.R;

/**
 * Created by scwen on 2018/7/26.
 * QQ ：811733738
 * 作用：
 */

public class SizeFilter extends Filter {

    private int mMaxSize;

   public SizeFilter(int maxSizeInBytes) {

        mMaxSize = maxSizeInBytes;
    }


    @Override
    protected Set<MimeType> constraintTypes() {
        return MimeType.of(MimeType.MP4,MimeType.PNG,MimeType.JPEG);
    }

    @Override
    public IncapableCause filter(Context context, Item item) {
        if (!needFiltering(context, item))
            return null;

        if (item.size > mMaxSize) {
            return new IncapableCause(IncapableCause.DIALOG, context.getString(R.string.error_original,
                    String.valueOf(PhotoMetadataUtils.getSizeInMB(mMaxSize))));
        }
        return null;
    }
}
