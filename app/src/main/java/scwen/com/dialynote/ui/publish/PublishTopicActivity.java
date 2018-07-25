package scwen.com.dialynote.ui.publish;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import scwen.com.dialynote.R;
import scwen.com.dialynote.adapter.publish.ChooseImageAdapter;
import scwen.com.dialynote.appbase.BaseMvpActivity;
import scwen.com.dialynote.contract.PublishContract;
import scwen.com.dialynote.dialog.LoadingDialog;
import scwen.com.dialynote.presenter.PublishPresenter;
import scwen.com.dialynote.utils.ScreenUtils;
import scwen.com.dialynote.utils.UIUtils;
import scwen.com.dialynote.weight.divider.Api20ItemDivider;
import scwen.com.dialynote.weight.divider.Api21ItemDivider;
import scwen.com.dialynote.weight.divider.Divider;

public class PublishTopicActivity extends BaseMvpActivity<PublishPresenter> implements PublishContract.PublishView {


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


    public static void start(Context context) {
        Intent intent = new Intent(context, PublishTopicActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initView() {
        loadingDialog = new LoadingDialog(this);
        loadingDialog.setCanceledOnTouchOutside(false);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setTitle(R.string.shortcut_label_publish_topic);


        lable = getResources().getDrawable(R.drawable.icon_lable);
        lable.setBounds(0, 0, lable.getMinimumWidth(), lable.getMinimumHeight());
        lableActive = getResources().getDrawable(R.drawable.icon_lable_active);
        lableActive.setBounds(0, 0, lableActive.getMinimumWidth(), lableActive.getMinimumHeight());
        location = getResources().getDrawable(R.drawable.icon_location);
        location.setBounds(0, 0, location.getMinimumWidth(), location.getMinimumHeight());


        locationActive = getResources().getDrawable(R.drawable.icon_location_active);
        locationActive.setBounds(0, 0, locationActive.getMinimumWidth(), locationActive.getMinimumHeight());


        //初始化 9图 RecyclerView
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        Divider divider = getDivider(Color.WHITE);
        mRecyclerView.addItemDecoration(divider);
        int itemSize = (ScreenUtils.getScreenWidth(this) - (divider.getWidth() * 3) - UIUtils.dip2px(30)) / 4;
        mAdapter = new ChooseImageAdapter(this, itemSize, 9, new ChooseImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //预览
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

    @NonNull
    public static Divider getDivider(@ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int size = UIUtils.dip2px(6);
            return new Api21ItemDivider(color, size, size);
        } else {
            int size = UIUtils.dip2px(2);
            return new Api20ItemDivider(color, size, size);
        }
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
                Toast.makeText(PublishTopicActivity.this, "发布", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
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


    @OnClick({R.id.tv_location, R.id.tv_lable, R.id.iv_capture, R.id.iv_choose_album})
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
                break;
        }
    }
}
