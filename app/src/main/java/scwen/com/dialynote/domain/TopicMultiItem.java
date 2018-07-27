package scwen.com.dialynote.domain;

/**
 * Created by 解晓辉  on 2017/10/9 11:25 *
 * QQ  ：811733738
 * 作用:
 */

public class TopicMultiItem<T> implements com.chad.library.adapter.base.entity.MultiItemEntity {

    public static final int ITEM_HEADER = 0;//您关注的用户未发布话题...
    public static final int ITEM_FOCUS = 1;//您可能感兴趣的用户
    public static final int ITEM_TOPIC= 2;//图片
    public static final int ITEM_END = 3;//end


    private int itemType;

    private T t;


    public TopicMultiItem(int itemType, T t) {
        this.itemType = itemType;
        this.t = t;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
