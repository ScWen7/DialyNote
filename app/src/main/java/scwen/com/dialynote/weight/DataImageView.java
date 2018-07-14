package scwen.com.dialynote.weight;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;


/**
 * 自定义ImageView，可以存放Bitmap和Path等信息
 */
public class DataImageView extends AppCompatImageView {


    private String imagePath;


    public String getImagePath() {
        return imagePath == null ? "" : imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public DataImageView(Context context) {
        super(context);
    }

    public DataImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DataImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


}
