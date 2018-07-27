package scwen.com.dialynote.weight;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import scwen.com.dialynote.R;
import scwen.com.dialynote.domain.ImageInfo;
import scwen.com.dialynote.transform.CustomerCenterCrop;
import scwen.com.dialynote.utils.UIUtils;

/**
 * Created by xxh on 2018/6/12.
 */

public class TopicContainrView extends CardView {

    @BindView(R.id.rl_head)
    RelativeLayout mRlHead;
    @BindView(R.id.iv_avatar)
    CircleImageView mIvAvatar;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_topic_time)
    TextView mTvTopicTime;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_main_body)
    TextView mTvMainBody;
    @BindView(R.id.nineGrid)
    MultiImageView mNineGrid;
    @BindView(R.id.iv_video)
    ImageView mIvVideo;
    @BindView(R.id.rl_video)
    RelativeLayout mRlVideo;
    @BindView(R.id.tv_address_info)
    TextView mTvAddressInfo;
    @BindView(R.id.tv_label)
    TextView mTvLabel;
    @BindView(R.id.ll_item)
    LinearLayout mLlItem;


    private OnContainerClickListener mOnContainerClickListener;
    private int gray;


    public void setOnContainerClickListener(OnContainerClickListener onContainerClickListener) {
        mOnContainerClickListener = onContainerClickListener;
    }

    public TopicContainrView(Context context) {
        super(context);
        init(context, null);
    }

    public TopicContainrView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }


    public TopicContainrView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    /**
     * 执行初始化操作
     *
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.topic_content, this, true);
        ButterKnife.bind(this);
        gray = ContextCompat.getColor(context, R.color.ff888888);
    }

    /**
     * @param username  用户名称
     * @param time      发布时间
     * @param label     标签
     */
    public TopicContainrView bindCommonData(String username, String avater,  String time, String label) {
        mTvName.setText(username);
//        Glide.with(getContext())
//                .load(avater)
//                .asBitmap()
//                .into(mIvAvatar);
//        if ("0".equals(isMember)) {
//
//            mIvIsMember.setVisibility(GONE);
//        } else if ("1".equals(isMember)) {
//            mIvIsMember.setVisibility(VISIBLE);
//        }
        mTvTopicTime.setText(time);
        mTvLabel.setText("#" + label + "#");
//        mTvSeeNo.setText(hits);
//        mTvCommentNo.setText(comNumber);
        return this;
    }


    /**
     * 绑定 头像和 整个 item 的点击事件
     *
     * @param topicId
     * @param uid
     * @return
     */
    public TopicContainrView bindClick(final String topicId, final String uid, final String labelid) {
        if (!TextUtils.isEmpty(uid)) {
//            mRlAvatar.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    UserHomePageActivity.start(getContext(), uid, 1);
//                }
//            });
        }

        mLlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                TopicDellsActivity.start(getContext(), topicId);
            }
        });

        if (!TextUtils.isEmpty(labelid)) {

            mTvLabel.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
//                    LableListActivity.start(getContext(), labelid);
                }
            });
        }
        return this;
    }


    /**
     * 绑定 title 的显示
     */
    public TopicContainrView bindTitle(String title) {
        if (TextUtils.isEmpty(title)) {
            mTvTitle.setVisibility(GONE);
        } else {
            mTvTitle.setVisibility(VISIBLE);
            mTvTitle.setText(title);
        }
        return this;
    }

    /**
     * 绑定 地址 的显示
     */
    public TopicContainrView bindLocation(String place, String range) {
        if (TextUtils.isEmpty(place)) {
            mTvAddressInfo.setVisibility(GONE);
        } else {
            mTvAddressInfo.setVisibility(VISIBLE);
            if (range.isEmpty()) {
                mTvAddressInfo.setText(place);
            } else {
                mTvAddressInfo.setText(place + " | " + range + "Km");
            }
        }
        return this;
    }

    /**
     * 用户中心 头像那一栏隐藏并且标签也隐藏
     */
    public TopicContainrView setHeadGone(String time) {
        mRlHead.setVisibility(GONE);
        mTvLabel.setText(time);
        mTvLabel.setTextColor(gray);
        return this;
    }

    /**
     * 热门 列表中 绑定是否显示 推荐标签
     */
    public TopicContainrView bindIsRecommend(String isRecommend) {
//        if ("1".equals(isRecommend)) {
//            mIvIsRecommend.setVisibility(VISIBLE);
//        } else {
//            mIvIsRecommend.setVisibility(GONE);
//        }
        return this;
    }

    /**
     * 绑定正文内容
     *
     * @param content
     * @return
     */
    public TopicContainrView bindContent(String content) {

        if (content.contains("<br/>")) {
            content = content.replace("<br/>", "\n");
        }
        if (content.contains("&nbsp;")) {
            content = content.replace("&nbsp;", "  ");
        }

        if (TextUtils.isEmpty(content)) {
            mTvMainBody.setVisibility(View.GONE);
        } else {
            mTvMainBody.setVisibility(View.VISIBLE);
            if (content.length() < 55) {
                mTvMainBody.setText(content);
            } else {

                content = content.substring(0, 55) + "...全文";
                SpannableStringBuilder ss = new SpannableStringBuilder(content);
                ss.setSpan(new ForegroundColorSpan(UIUtils.getColor(R.color.ff4285F4)), 55, content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                mTvMainBody.setText(ss);
            }
        }

        return this;
    }

    /**
     * 绑定点赞数据  包括点赞的点击事件
     *
     * @param topicId
     * @param isFab
     * @param fabNumber
     * @return
     */
    public TopicContainrView bindLike(final String topicId, String isFab, String fabNumber) {

        if (isFab == null) {
            return this;
        }
//        mTvLikeNo.setText(fabNumber);
//        if ("1".equals(isFab)) {
//            mIvLike.setImageResource(R.drawable.icon_topic_like);
//        } else if ("0".equals(isFab)) {
//            mIvLike.setImageResource(R.drawable.icon_topic_unlike);
//        }
//
//        mIvLike.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (Commons.isLogin()) {
//                    setLikeData(topicId);
//                } else {
//                    Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
//                    LoginActivity.start(getContext());
//                }
//            }
//        });


        return this;
    }

//    /**
//     * 点赞接口
//     */
//    private void setLikeData(String topicId) {
//        BaseRequestObject object = new BaseRequestObject();
//        object.put("tid", topicId);
//        OKNetUtils.doGet((OKTag) getContext(), "Community/Fabulous", object, false, FabulousResponse.class, new GetDataCallback<FabulousResponse>() {
//            @Override
//            public void onSuccess(FabulousResponse fabulousResponse) {
//                if (mIvLike == null) {
//                    return;
//                }
//                if ("1".equals(fabulousResponse.getIsfabulous())) {
//                    mIvLike.setImageResource(R.drawable.icon_topic_like);
//                } else if ("0".equals(fabulousResponse.getIsfabulous())) {
//                    mIvLike.setImageResource(R.drawable.icon_topic_unlike);
//                }
//
//                if ("1".equals(fabulousResponse.getIsfabulous())) {
//                    scaleAnimtor(mIvLike);
//                }
//                if (mOnContainerClickListener != null) {
//                    mOnContainerClickListener.onLikeClick(fabulousResponse.getIsfabulous(), fabulousResponse.getFabulousno());
//                }
//
//
//            }
//
//            @Override
//            public void onError(Response response) {
//                Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    /**
     * 绑定  图片和视频的显示
     *
     * @param topid
     * @param type
     * @param images
     * @param videos
     * @return
     */
    public TopicContainrView bindImageOrVideo(String topid, String type, List<String> images, List<String> videos) {
        if ("1".equals(type)) {
            showImages(topid, type, images);
        } else if ("2".equals(type)) {
            if (videos.size() >= 2) {
                showVideo(videos.get(0), videos.get(1));
            }
        } else if ("3".equals(type)) {
            if (images != null && images.size() > 0) {
                showImages(topid, type, images);
            } else if (videos != null && videos.size() > 0) {
//                String videoPoster = TopicStringUtils.getVideoPoster(videos.get(0));
//                if (TextUtils.isEmpty(videoPoster)) {
//                    videoPoster = "http://statics.artbloger.com/images/video_thumb.jpg";
//                }
//                showVideo(videoPoster, TopicStringUtils.getVideoSrc(videos.get(0)));
            } else {
                //TODO 解决第一页最后一个item错乱问题
                mNineGrid.setVisibility(GONE);
                mRlVideo.setVisibility(GONE);
            }
        } else {
            mNineGrid.setVisibility(GONE);
            mRlVideo.setVisibility(GONE);
        }
        return this;
    }

    /**
     * 显示视频
     *
     * @param placeHolder 缩略图地址
     * @param videoPath   视频地址
     */
    private void showVideo(final String placeHolder, final String videoPath) {
        mNineGrid.setVisibility(GONE);
        mRlVideo.setVisibility(VISIBLE);
        Glide.with(getContext())
                .load(placeHolder)
                .transform(new CustomerCenterCrop(getContext()))
                .into(mIvVideo);

        mIvVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(videoPath)) {
//                    VideoPlayActivity.start(getContext(), placeHolder, videoPath);
                }
            }
        });
    }

    /**
     * 显示图片
     *
     * @param topid  动态 id
     * @param type   动态类型
     * @param images 图片链接集合
     */
    private void showImages(String topid, String type, List<String> images) {
        mNineGrid.setVisibility(VISIBLE);
        mRlVideo.setVisibility(GONE);
        ArrayList<ImageInfo> imageInfo = new ArrayList<>();
        if (images != null) {
            for (String image : images) {
                if ("3".equals(type)) {
                    //type =3 的时候需要从 <img> 标签中取出 src 链接
                    if (image.contains(",")) {//多个<img>
                        String[] srcs = image.split(",");
                        for (int i = 0; i < srcs.length; i++) {

                            ImageInfo info = new ImageInfo();
                            info.setBigImageUrl(topid);//为了跳转话题详情页

                            System.out.println("~~~~~srcs~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + srcs[i]);

//                            String imgSrc = TopicStringUtils.getImgSrc(srcs[i]);

//                            if (imgSrc.contains("2000_1600_1")) {
//                                imgSrc = imgSrc.replace("2000_1600_1", "800_400_1");
//                            }
//                            info.setThumbnailUrl(imgSrc);
                            imageInfo.add(info);
                            if (imageInfo.size() >= 3) {
                                break;
                            }
                        }
                    } else {//单个<img>
                        ImageInfo info = new ImageInfo();
                        info.setBigImageUrl(topid);//为了跳转话题详情页
//                        info.setThumbnailUrl(TopicStringUtils.getImgSrc(image));
                        imageInfo.add(info);
                    }
                } else {
                    ImageInfo info = new ImageInfo();
                    info.setBigImageUrl(topid);//为了跳转话题详情页
                    info.setThumbnailUrl(image);
                    imageInfo.add(info);
                }

                if (imageInfo.size() >= 3) {
                    break;
                }
            }
        }
        mNineGrid.setList(imageInfo);
    }


    /**
     * 点赞点击监听
     */
    public interface OnContainerClickListener {
        /**
         * 点赞操作 回调
         *
         * @param isFab      是否点赞
         * @param fabNnumber 点赞数量
         */
        void onLikeClick(String isFab, String fabNnumber);
    }

    /**
     * 属性动画，实现缩放的效果
     */
    private void scaleAnimtor(View v) {
        ObjectAnimator oaX = ObjectAnimator.ofFloat(v, "scaleX", 1.0f, 1.5f, 1.0f);
        oaX.setDuration(800);
        ObjectAnimator oaY = ObjectAnimator.ofFloat(v, "scaleY", 1.0f, 1.5f, 1.0f);
        oaY.setDuration(800);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(oaX, oaY);
        set.setInterpolator(new LinearInterpolator());
        set.start();
    }


}
