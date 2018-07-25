package scwen.com.dialynote.ui.publish;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import butterknife.BindView;
import scwen.com.dialynote.R;
import scwen.com.dialynote.appbase.BaseMvpActivity;
import scwen.com.dialynote.contract.PublishContract;
import scwen.com.dialynote.dialog.LoadingDialog;
import scwen.com.dialynote.presenter.PublishPresenter;

public class PublishTopicActivity extends AppCompatActivity {




//    BaseMvpActivity<PublishPresenter> implements PublishContract.PublishView
    private LoadingDialog loadingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
    }


    public static void start(Context context) {
        Intent intent = new Intent(context, PublishTopicActivity.class);
        context.startActivity(intent);
    }

//    @Override
//    protected void initView() {
//        loadingDialog = new LoadingDialog(this);
//        loadingDialog.setCanceledOnTouchOutside(false);
//
//
//
//    }


    //如果有Menu,创建完后,系统会自动添加到ToolBar上
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.activity_publish_topic, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                Toast.makeText(PublishTopicActivity.this, "退出", Toast.LENGTH_SHORT).show();
                break;
            case R.id.publish:
                Toast.makeText(PublishTopicActivity.this, "发布", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

//    @Override
    protected int getLayoutId() {
        return R.layout.activity_publish_topic;
    }

//    @Override
//    protected void initData() {
//
//    }
//
//    @Override
//    public PublishPresenter createPresenter() {
//        return new PublishPresenter(this);
//    }
//
//    @Override
//    public void showLoading() {
//        if (loadingDialog != null && !loadingDialog.isShowing()) {
//            loadingDialog.show();
//        }
//    }
//
//    @Override
//    public void dismissLoading() {
//        if (loadingDialog != null && loadingDialog.isShowing()) {
//            loadingDialog.dismiss();
//        }
//    }
}
