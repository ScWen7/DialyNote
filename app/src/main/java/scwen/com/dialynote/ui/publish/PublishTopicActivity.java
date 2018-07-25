package scwen.com.dialynote.ui.publish;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
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
import scwen.com.dialynote.appbase.BaseMvpActivity;
import scwen.com.dialynote.contract.PublishContract;
import scwen.com.dialynote.dialog.LoadingDialog;
import scwen.com.dialynote.presenter.PublishPresenter;

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
