package scwen.com.dialynote.utils;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by yiwei on 16/3/2.
 */
public class CommentConfig {

    public static final int COMMENT_PUBLIC = 10001;
    public static final int COMMENT_REPLY = 10002;


    @IntDef({COMMENT_PUBLIC, COMMENT_REPLY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface CommentType {
    }

    public int circlePosition;
    public int commentPosition;
    private int commentType;
    public User replyUser;

    public int getCommentType() {
        return commentType;
    }

    public void setCommentType(@CommentType int commentType) {
        this.commentType = commentType;
    }

    @Override
    public String toString() {
        String replyUserStr = "";
        if (replyUser != null) {
            replyUserStr = replyUser.toString();
        }
        return "circlePosition = " + circlePosition
                + "; commentPosition = " + commentPosition
                + "; commentType ＝ " + commentType
                + "; replyUser = " + replyUserStr;
    }
}
