package scwen.com.dialynote.ui.publish;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.zhihu.matisse.internal.entity.Album;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import scwen.com.dialynote.R;
import scwen.com.dialynote.adapter.publish.ImagePreviewAdapter;
import scwen.com.dialynote.appbase.BaseActivity;
import scwen.com.dialynote.utils.StatusBarUtil;
import scwen.com.dialynote.weight.FixViewPager;

public class ImagePreviewActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.view_pager)
    FixViewPager mViewPager;
    @BindView(R.id.tv_preview_index)
    TextView mTvPreviewIndex;

    private ImagePreviewAdapter previewAdapter;

    public static final String IMAGE_PATHS = "file_paths"; //启动的参数

    public static final String CURRENT_POSTION = "current_position";


    private ArrayList<String> mPaths;
    private int mCurrentItemPosition;

    private boolean isModify = false;


    public static void start(Activity activity, ArrayList<String> paths, int currentPosition, int requestCode) {
        Intent intent = new Intent(activity, ImagePreviewActivity.class);

        intent.putStringArrayListExtra(IMAGE_PATHS, paths);
        intent.putExtra(CURRENT_POSTION, currentPosition);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void initView() {
        StatusBarUtil.setTranslucentForImageView(this, 0, mToolbar);
        getWindow().setNavigationBarColor(Color.BLACK);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mPaths = getIntent().getStringArrayListExtra(IMAGE_PATHS);
        mCurrentItemPosition = getIntent().getIntExtra(CURRENT_POSTION, 0);

        if (mPaths == null) {
            finish();
            return;

        }

        initializeViewPager();


    }

    private void initializeViewPager() {
        if (mPaths != null) {
            if (mPaths.size() > 3)
                mViewPager.setOffscreenPageLimit(3);
            else if (mPaths.size() > 2)
                mViewPager.setOffscreenPageLimit(2);
        }

        previewAdapter = new ImagePreviewAdapter(this, mPaths);
        mViewPager.setAdapter(previewAdapter);
        ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mCurrentItemPosition = position;
                setTitle();
            }
        };
        mViewPager.addOnPageChangeListener(pageChangeListener);
        mViewPager.setCurrentItem(mCurrentItemPosition);
        // Forced call.
        pageChangeListener.onPageSelected(mCurrentItemPosition);
    }

    private void onResult() {
        Intent intent = new Intent();
        intent.putStringArrayListExtra(IMAGE_PATHS, mPaths);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (isModify) {
            onResult();
        } else {
            super.onBackPressed();
        }
    }

    public void setTitle() {
        mTvPreviewIndex.setText(mCurrentItemPosition + 1 + " / " + mPaths.size());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_image_preview;
    }

    //如果有Menu,创建完后,系统会自动添加到ToolBar上
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_preview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                finish();
                break;
            case R.id.delete:
                isModify = true;
                mPaths.remove(mCurrentItemPosition);
                if (mPaths.size() > 0) {
                    previewAdapter.setData(mPaths);
                    previewAdapter.notifyDataSetChanged();
                    mTvPreviewIndex.setText(mCurrentItemPosition + 1 + " / " + mPaths.size());
                } else {
                    onResult();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
