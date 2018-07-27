package scwen.com.dialynote.utils;

import android.content.Context;
import android.webkit.MimeTypeMap;

import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.IncapableCause;
import com.zhihu.matisse.internal.entity.Item;
import com.zhihu.matisse.internal.utils.PathUtils;
import com.zhihu.matisse.internal.utils.PhotoMetadataUtils;

import java.util.Set;

import scwen.com.dialynote.R;

/**
 * Created by scwen on 2018/7/27.
 * QQ ：811733738
 * 作用：
 */

public class MimeTypeFilter extends Filter {
    @Override
    protected Set<MimeType> constraintTypes() {
        return MimeType.of(MimeType.MP4,MimeType.PNG,MimeType.JPEG);
    }

    @Override
    public IncapableCause filter(Context context, Item item) {
        if (!needFiltering(context, item))
            return null;

        String path = PathUtils.getPath(context, item.getContentUri());
        String extension = MimeTypeMap.getFileExtensionFromUrl(path);
        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        if(mimeType==null) {
            return new IncapableCause(IncapableCause.DIALOG,"文件格式不支持");
        }

        return null;
    }
}
