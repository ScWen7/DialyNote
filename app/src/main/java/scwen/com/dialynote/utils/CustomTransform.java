package scwen.com.dialynote.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;

/**
 * Created by xxh on 2018/4/25.
 */

public class CustomTransform extends BitmapTransformation {
    private int screenWidth;
    public static final int PAINT_FLAGS = Paint.DITHER_FLAG | Paint.FILTER_BITMAP_FLAG;

    public CustomTransform(Context context, int imageWidth) {
        super(context);
        screenWidth = imageWidth;
    }


    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        int width = toTransform.getWidth(); // 图片的宽
        int height = toTransform.getHeight(); // 图片的高

        float ratio = height / (float) width;

        Log.e("TAG", "ratio:" + ratio);

        int toWidth = 0;
        if (ratio >= 7) {
            toWidth = (int) (screenWidth / ratio);
            toWidth = toWidth * 7;
        } else {
            toWidth = screenWidth;
        }

        float scale = toWidth / (float) width;

        width = toWidth;

        // 如果图片的宽小于等于高，则以宽为基准，以形状的宽高比重新设置高度度
        height = (int) (ratio * toWidth);
        if (toTransform == null) {
            return null;
        } else if (toTransform.getWidth() == width && toTransform.getHeight() == height) {
            return toTransform;
        }

        Matrix m = new Matrix();
        m.setScale(scale, scale);

        final Bitmap toReuse = pool.get(width, height, Bitmap.Config.RGB_565);
        final Bitmap result;
        if (toReuse != null) {
            result = toReuse;
        } else {
            result = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        }
        TransformationUtils.setAlpha(toTransform, result);
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint(PAINT_FLAGS);
        canvas.drawBitmap(toTransform, m, paint);

//        if (toReuse != null && !pool.put(toReuse)) {
//            toReuse.recycle();
//        }
        if (toReuse != null && toReuse != result && !pool.put(toReuse)) {
            toReuse.recycle();
        }

        return result;
    }

    @Override
    public String getId() {
        return getClass().getName();
    }
}
