package scwen.com.dialynote.utils;

import android.content.Context;
import android.view.View;

import com.art.activity.ImagePagerActivity;

import java.util.List;

/**
 * Created by xxh on 2018/4/24.
 */

public class ImagePreClickListener implements View.OnClickListener {

    private int startPos;
    private List<String> imgUrls;

    private Context mContext;

    public ImagePreClickListener(Context context, int startPos, List<String> imgUrls) {
        mContext = context;
        this.startPos = startPos;
        this.imgUrls = imgUrls;
    }

    @Override
    public void onClick(View v) {
        ImagePagerActivity.start(mContext, imgUrls, startPos);
    }
}
