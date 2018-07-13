package scwen.com.dialynote.utils;

/**
 * Created by xxh on 2018/4/24.
 */

public interface CommentView {
    void updateEditTextBodyVisible(int visibility, CommentConfig commentConfig);


    void loadMoreReply(int circlePosition, String cid, String rid);
}
