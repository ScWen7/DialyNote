package scwen.com.dialynote.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.art.utils.ScreenUtils;
import com.art.utils.UIUtils;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;

/**
 * Scale the image so that either the width of the image matches the given width and the height of the image is
 * greater than the given height or vice versa, and then crop the larger dimension to match the given dimension.
 * <p>
 * Does not maintain the image's aspect ratio
 */
public class EditerTranform extends BitmapTransformation {

    private int imageContentWidth;


    public EditerTranform(Context context, int padding) {
        super(context);
        imageContentWidth = ScreenUtils.getScreenWidth(context) - UIUtils.dip2px(padding);
    }

    public EditerTranform(BitmapPool bitmapPool) {
        super(bitmapPool);
    }

    // Bitmap doesn't implement equals, so == and .equals are equivalent here.
    @SuppressWarnings("PMD.CompareObjectsWithEquals")
    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {

        int width = toTransform.getWidth(); // 图片的宽
        int height = toTransform.getHeight(); // 图片的高


        if (width > imageContentWidth) {
            outWidth = imageContentWidth;
            float scale = outWidth / (float) width;
            outHeight = (int) (scale * height);
        } else {
            outWidth = width;
            outHeight = height;
        }

        Log.e("TAG", "width:" + width);
        Log.e("TAG", "height:" + outHeight);
        Log.e("TAG", "outWidth:" + outWidth);
        Log.e("TAG", "outHeight:" + outHeight);


        final Bitmap toReuse = pool.get(outWidth, outHeight, toTransform.getConfig() != null
                ? toTransform.getConfig() : Bitmap.Config.ARGB_8888);
        Bitmap transformed = TransformationUtils.centerCrop(toReuse, toTransform, outWidth, outHeight);
        if (toReuse != null && toReuse != transformed && !pool.put(toReuse)) {
            toReuse.recycle();
        }
        return transformed;
    }

    @Override
    public String getId() {
        return "com.art.commentweight.EditerTranform";
    }
}
