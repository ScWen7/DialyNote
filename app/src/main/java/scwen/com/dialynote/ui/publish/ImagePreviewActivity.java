package scwen.com.dialynote.ui.publish;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.BindView;
import scwen.com.dialynote.R;
import scwen.com.dialynote.appbase.BaseActivity;
import scwen.com.dialynote.utils.StatusBarUtil;

public class ImagePreviewActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;


    public static void start(Context context) {
        Intent intent = new Intent(context, ImagePreviewActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initView() {
        StatusBarUtil.setTranslucentForImageView(this, 0, mToolbar);
        getWindow().setNavigationBarColor(Color.BLACK);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
                Toast.makeText(ImagePreviewActivity.this, "删除", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
