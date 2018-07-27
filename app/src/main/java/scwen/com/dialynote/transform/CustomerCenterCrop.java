package scwen.com.dialynote.transform;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;

import scwen.com.dialynote.utils.UIUtils;

/**
 * Scale the image so that either the width of the image matches the given width and the height of the image is
 * greater than the given height or vice versa, and then crop the larger dimension to match the given dimension.
 * <p>
 * Does not maintain the image's aspect ratio
 */
public class CustomerCenterCrop extends BitmapTransformation {
    private int onePicWidth;
    private int onePicHeight;

    public CustomerCenterCrop(Context context) {
        super(context);
        onePicWidth = UIUtils.dip2px(150);
        onePicHeight = UIUtils.dip2px(255);
    }

    public CustomerCenterCrop(BitmapPool bitmapPool) {
        super(bitmapPool);
    }

    // Bitmap doesn't implement equals, so == and .equals are equivalent here.
    @SuppressWarnings("PMD.CompareObjectsWithEquals")
    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        int width = toTransform.getWidth(); // 图片的宽
        int height = toTransform.getHeight(); // 图片的高
        float ratio = height / (float) width;
        if (width > height) {
            outHeight = onePicWidth;
            if (ratio <= 1.7) {
                float scale = outHeight / (float) height;
                outWidth = (int) (scale * width);
            } else {
                outWidth = onePicHeight;
            }
        } else {
            outWidth = onePicWidth;
            if (ratio <= 1.7) {
                float scale = outWidth / (float) width;
                outHeight = (int) (scale * height);
            } else {
                outHeight = onePicHeight;
            }
        }

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
        return "com.art.commentweight.CustomerCenterCrop";
    }
}
