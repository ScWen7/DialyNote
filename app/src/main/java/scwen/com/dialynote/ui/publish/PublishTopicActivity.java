package scwen.com.dialynote.ui.publish;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.bumptech.glide.Glide;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.listener.OnCheckedListener;
import com.zhihu.matisse.listener.OnSelectedListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import scwen.com.dialynote.R;
import scwen.com.dialynote.adapter.publish.ChooseImageAdapter;
import scwen.com.dialynote.appbase.BaseMvpActivity;
import scwen.com.dialynote.contract.PublishContract;
import scwen.com.dialynote.dialog.LoadingDialog;
import scwen.com.dialynote.domain.TopicBean;
import scwen.com.dialynote.event.PublishTopicEvent;
import scwen.com.dialynote.presenter.PublishPresenter;
import scwen.com.dialynote.utils.ScreenUtils;
import scwen.com.dialynote.utils.SizeFilter;
import scwen.com.dialynote.utils.UIUtils;
import scwen.com.dialynote.utils.VideoDurationFilter;
import scwen.com.dialynote.weight.divider.Divider;

public class PublishTopicActivity extends BaseMvpActivity<PublishPresenter> implements PublishContract.PublishView {

    private static final int REQUEST_PERMISSION_VIDEO = 99;  //请求拍照和录制视频权限
    private static final int REQUEST_CODE_SELECT = 100;  //选择照片请求码
    private static final int REQUEST_CODE_CAMERA = 200;  //拍照请求码
    private static final int REQUEST_CODE_PREIMAGE = 300; //预览照片请求码
    private static final int REQUEST_CODE_PREVIDEO = 400; //预览视频请求码


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.edt_content)
    EditText mEdtContent;
    @BindView(R.id.iv_video_thumb)
    ImageView mIvVideoThumb;
    @BindView(R.id.view_fraction)
    View mViewFraction;
    @BindView(R.id.fr_video)
    FrameLayout mFrVideo;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_location)
    TextView mTvLocation;
    @BindView(R.id.tv_lable)
    TextView mTvLable;
    //
    private LoadingDialog loadingDialog;


    private ChooseImageAdapter mAdapter;
    public static final int TOPIC_TEXT = 0;//纯文本类型
    public static final int TOPIC_IMAGE = 1;//图片类型
    public static final int TOPIC_VIDEO = 2;//视频类型

    //发布话题的类型
    private int topicType = TOPIC_TEXT;

    // 标签和 位置 drawable
    private Drawable lable;
    private Drawable lableActive;

    private Drawable location;
    private Drawable locationActive;
    private String mTopicPlace; //发布文章的 位置
    private double mLatitude;  //纬度
    private double mLongitude;  //经度

    private List<String> mPathList = new ArrayList<>();


    public static void start(Context context) {
        Intent intent = new Intent(context, PublishTopicActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initView() {

        EventBus.getDefault().register(this);
        loadingDialog = new LoadingDialog(this);
        loadingDialog.setCanceledOnTouchOutside(false);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setTitle(R.string.shortcut_label_publish_topic);


        initDrawable();


        initRecycler();


    }

    private void initDrawable() {
        lable = getResources().getDrawable(R.drawable.icon_lable);
        lable.setBounds(0, 0, lable.getMinimumWidth(), lable.getMinimumHeight());
        lableActive = getResources().getDrawable(R.drawable.icon_lable_active);
        lableActive.setBounds(0, 0, lableActive.getMinimumWidth(), lableActive.getMinimumHeight());
        location = getResources().getDrawable(R.drawable.icon_location);
        location.setBounds(0, 0, location.getMinimumWidth(), location.getMinimumHeight());


        locationActive = getResources().getDrawable(R.drawable.icon_location_active);
        locationActive.setBounds(0, 0, locationActive.getMinimumWidth(), locationActive.getMinimumHeight());
    }

    private void initRecycler() {
        //初始化 9图 RecyclerView
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        Divider divider = UIUtils.getDivider(Color.WHITE);
        mRecyclerView.addItemDecoration(divider);
        int itemSize = (ScreenUtils.getScreenWidth(this) - (divider.getWidth() * 3) - UIUtils.dip2px(30)) / 4;
        mAdapter = new ChooseImageAdapter(this, itemSize, 9, new ChooseImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //预览
                previewAlbum(position);
            }
        });
        mAdapter.setHasStableIds(true);
        mAdapter.setmAddImageListener(new ChooseImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //添加 文件
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }


    /**
     * 预览图片
     *
     * @param position
     */
    private void previewAlbum(int position) {
        ImagePreviewActivity.start(this, new ArrayList<String>(mPathList), position, REQUEST_CODE_PREIMAGE);
    }


    //如果有Menu,创建完后,系统会自动添加到ToolBar上
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_publish_topic, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                finish();
                break;
            case R.id.publish:

                publishTopic();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Subscribe
    public void onChooseLocationEvent(PoiItem poiItem) {

        LatLonPoint latLonPoint = poiItem.getLatLonPoint();

        if (latLonPoint != null) {
            mTopicPlace = poiItem.getTitle();
            //选择地址的 纬度
            mLatitude = latLonPoint.getLatitude();

            //选择地址的经度
            mLongitude = latLonPoint.getLongitude();
            String showLocation = mTopicPlace;
            if (!TextUtils.isEmpty(mTopicPlace) && mTopicPlace.length() > 4) {
                showLocation = showLocation.substring(0, 4) + "...";
            }
            mTvLocation.setText(showLocation);
            mTvLocation.setTextColor(UIUtils.getColor(R.color.ff4285F4));
            mTvLocation.setCompoundDrawables(locationActive, null, null, null);
        } else {
            mTopicPlace = null;
            mTvLocation.setText(R.string.location);
            mTvLocation.setTextColor(UIUtils.getColor(R.color.ff969696));
            mTvLocation.setCompoundDrawables(location, null, null, null);
        }


    }

    /**
     * 发布
     */
    private void publishTopic() {

        String content = mEdtContent.getText().toString();

        TopicBean topicBean = new TopicBean();
        topicBean.setContent(content);
        topicBean.setPublishTime(new Date().getTime());
        topicBean.setInfoType("0");
        topicBean.setType(topicType);
        switch (topicType) {
            case TOPIC_IMAGE:
                topicBean.setImages(mPathList);
                break;
            case TOPIC_VIDEO:
                topicBean.setVideos(mPathList);
                break;

        }
        topicBean.save();

        EventBus.getDefault().post(new PublishTopicEvent());


        finish();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_publish_topic;
    }

    @Override
    protected void initData() {

    }

    @Override
    public PublishPresenter createPresenter() {
        return new PublishPresenter(this);
    }

    @Override
    public void showLoading() {
        if (loadingDialog != null && !loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    @Override
    public void dismissLoading() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT && resultCode == RESULT_OK) {
            //根据 返回的数据进行 显示

            List<String> pathList = Matisse.obtainPathResult(data);

            List<Uri> uris = Matisse.obtainResult(data);

            if (pathList != null && pathList.size() > 0) {
                if (pathList.size() == 1) {
                    //因为 视频是单选，当 文件数量为1 时，进行文件类型的判断

                    mPathList.addAll(pathList);
                    String file = pathList.get(0);

                    String extension = MimeTypeMap.getFileExtensionFromUrl(file);
                    String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
                    if (!TextUtils.isEmpty(mimeType)) {
                        if (extension.contains("video")) {
                            //文件类型为 视频
                            Log.e("TAG", "视频");
                            topicType = TOPIC_VIDEO;
                            mFrVideo.setVisibility(View.VISIBLE);
                            mRecyclerView.setVisibility(View.GONE);
                            Glide.with(PublishTopicActivity.this).load(file).into(mIvVideoThumb);
                        } else {
                            //文件类型为  图片
                            Log.e("TAG", "图片");
                            topicType = TOPIC_IMAGE;
                            mFrVideo.setVisibility(View.GONE);
                            mRecyclerView.setVisibility(View.VISIBLE);
                            mAdapter.notifyDataSetChanged(mPathList);
                        }
                    }
                } else {
                    mPathList.addAll(pathList);
                    topicType = TOPIC_IMAGE;
                    mFrVideo.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mAdapter.notifyDataSetChanged(mPathList);
                }
            }

        } else if (requestCode == REQUEST_CODE_PREIMAGE && resultCode == RESULT_OK) {
            if (data == null) {
                return;
            }
            //预览图片回调
            ArrayList<String> extra = data.getStringArrayListExtra(ImagePreviewActivity.IMAGE_PATHS);
            if (extra != null) {
                mPathList = extra;
                if (extra.size() > 0) {
                    topicType = TOPIC_IMAGE;
                    mAdapter.notifyDataSetChanged(mPathList);
                } else {
                    topicType = TOPIC_TEXT;
                    mRecyclerView.setVisibility(View.GONE);
                }
            }
        } else if (requestCode == REQUEST_CODE_PREVIDEO && resultCode == RESULT_OK) {
            topicType = TOPIC_TEXT;
            mPathList.remove(0);
            mFrVideo.setVisibility(View.GONE);
        }
    }


    @OnClick({R.id.tv_location, R.id.tv_lable, R.id.iv_capture, R.id.iv_choose_album, R.id.fr_video})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_location:
                ChooseLocationActivity.start(this);
                break;
            case R.id.tv_lable:

                break;
            case R.id.iv_capture:

                break;
            case R.id.iv_choose_album:

                generPermissionAndChooseAlbum();
                break;
            case R.id.fr_video:
                VideoPreviewActivity.start(this, mPathList.get(0));
                break;
        }
    }

    /**
     * 申请 sd卡读取权限 成功之后选择图片
     */
    private void generPermissionAndChooseAlbum() {
        AndPermission.with(this)
                .runtime()
                .permission(Permission.Group.STORAGE)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        chooseAlbum();
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {

                    }
                })
                .start();
    }

    /**
     * 选择相册
     */
    private void chooseAlbum() {
        Divider divider = UIUtils.getDivider(Color.WHITE);
        int itemSize = (ScreenUtils.getScreenWidth(this) - divider.getWidth() * (4)) / 3;
        Matisse.from(PublishTopicActivity.this)
                .choose(MimeType.of(MimeType.MP4, MimeType.PNG, MimeType.JPEG), true)
                .countable(true)
                .capture(false)
                .theme(R.style.MyALbum)
                .maxSelectablePerMediaType(9, 1)
                .addFilter(new VideoDurationFilter(3000))
                .addFilter(new scwen.com.dialynote.utils.MimeTypeFilter())
                .addFilter(new SizeFilter(30 * Filter.K * Filter.K))
                .gridExpectedSize(
                        itemSize)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .setOnSelectedListener(new OnSelectedListener() {
                    @Override
                    public void onSelected(
                            @NonNull List<Uri> uriList, @NonNull List<String> pathList) {
                        // DO SOMETHING IMMEDIATELY HERE
                        Log.e("tag", "onSelected: pathList=" + pathList);


                    }
                })
                .originalEnable(false)
                .setOnCheckedListener(new OnCheckedListener() {
                    @Override
                    public void onCheck(boolean isChecked) {
                        // DO SOMETHING IMMEDIATELY HERE
                        Log.e("tag", "onCheck: isChecked=" + isChecked);
                    }
                })
                .forResult(REQUEST_CODE_SELECT);
    }
}
