package scwen.com.dialynote.utils;

import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;



/**
 * @author yiw
 * @Description:
 * @date 16/1/2 16:32
 */
public abstract class SpannableClickable extends ClickableSpan implements View.OnClickListener {

    private int DEFAULT_COLOR_ID = Color.GRAY;
    /**
     * text颜色
     */
    private int textColor;

    public SpannableClickable() {
        this.textColor = UIUtils.getColor(DEFAULT_COLOR_ID);
    }

    public SpannableClickable(int colorId) {
        this.textColor = UIUtils.getColor(colorId);
    }


    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);

        ds.setColor(textColor);
        ds.setUnderlineText(false);
        ds.clearShadowLayer();
    }
}
