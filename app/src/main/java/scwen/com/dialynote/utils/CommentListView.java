package scwen.com.dialynote.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.art.activity.R;
import com.art.bean.ReplyBean;
import com.art.utils.UIUtils;
import com.art.view.widget.CircleImageView;
import com.art.view.widget.CommentDialog;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yiwei on 16/7/9.
 */
public class CommentListView extends LinearLayout {
    private OnItemClickListener onItemClickListener;
    private List<ReplyBean> mDatas;
    private LayoutInflater layoutInflater;


    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public void setDatas(List<ReplyBean> datas) {
        if (datas == null) {
            datas = new ArrayList<ReplyBean>();
        }
        mDatas = datas;
        notifyDataSetChanged();
    }

    public List<ReplyBean> getDatas() {
        return mDatas;
    }

    public CommentListView(Context context) {
        super(context);
    }

    public CommentListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CommentListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void notifyDataSetChanged() {

        removeAllViews();
        if (mDatas == null || mDatas.size() == 0) {
            return;
        }
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < mDatas.size(); i++) {
            final int index = i;
            View view = getView(index);
            if (view == null) {
                throw new NullPointerException("listview item layout is null, please check getView()...");
            }

            addView(view, index, layoutParams);
        }

    }

    private View getView(final int position) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(getContext());
        }
        View convertView = layoutInflater.inflate(R.layout.item_detail_comment, this, false);

        CircleImageView userImage = convertView.findViewById(R.id.user_image);  //评论人的头像

        TextView tv_cur_user = convertView.findViewById(R.id.tv_cur_user);  //评论的人

        TextView tv_comment = convertView.findViewById(R.id.tv_comment); //评论内容

        TextView tv_time = convertView.findViewById(R.id.tv_time);  //时间

        TextView tv_comment_reply = convertView.findViewById(R.id.tv_comment_reply);  //回复按钮

        final ReplyBean bean = mDatas.get(position);

        Glide.with(getContext())
                .load(bean.getUserimgs())
                .centerCrop()
                .into(userImage);

        String name = bean.getUsername();
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(setClickableSpan(name, bean.getUid()));

        String toReplyName = "";
        if (!TextUtils.isEmpty(bean.getTo_username())) {
            toReplyName = bean.getTo_username();
        }
        if (!TextUtils.isEmpty(toReplyName)) {
            builder.append(" ");
            builder.append(setColorSpan("回复"));
            builder.append(" ");
            builder.append(setClickableSpan(toReplyName, bean.getTo_uid()));
        }

        tv_cur_user.setMovementMethod(new LinkMovementMethod());
        tv_cur_user.setText(builder);


        tv_comment.setText(bean.getContent());

        tv_comment.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                CommentDialog dialog = new CommentDialog(getContext(), bean.getContent());
                dialog.show();
                return true;
            }
        });


        userImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    //TODO  测试假数据
                    onItemClickListener.toUserPage(bean.getUid());
                }
            }
        });

        if ("1".equals(bean.getIs_reply())) {
            tv_comment_reply.setVisibility(VISIBLE);
        } else {
            tv_comment_reply.setVisibility(GONE);
        }

        tv_time.setText(bean.getTime());


        tv_comment_reply.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.replyComment(position);
                }
            }
        });


        return convertView;
    }

    @NonNull
    private SpannableString setClickableSpan(final String textStr, final String id) {
        SpannableString subjectSpanText = new SpannableString(textStr);
        subjectSpanText.setSpan(new SpannableClickable() {
                                    @Override
                                    public void onClick(View widget) {
                                        if (onItemClickListener != null) {
                                            //TODO  测试假数据
                                            onItemClickListener.toUserPage(id);
                                        }
                                    }
                                }, 0, subjectSpanText.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return subjectSpanText;
    }

    @NonNull
    private SpannableString setColorSpan(String textStr) {
        SpannableString subjectSpanText = new SpannableString(textStr);
        subjectSpanText.setSpan(new ForegroundColorSpan(UIUtils.getColor(R.color.blue_0088FF)), 0, subjectSpanText.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return subjectSpanText;
    }


    public static interface OnItemClickListener {
        void toUserPage(String userId);

        void replyComment(int commentPosition);

    }


}
