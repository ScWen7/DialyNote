package scwen.com.dialynote.weight;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.yanzhenjie.album.AlbumFile;

/**
 * 自定义ImageView，可以存放Bitmap和Path等信息
 */
public class DataImageView extends AppCompatImageView {

    private AlbumFile mAlbumFile;

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


    public AlbumFile getAlbumFile() {
        return mAlbumFile;
    }

    public void setAlbumFile(AlbumFile albumFile) {
        mAlbumFile = albumFile;
    }
}
