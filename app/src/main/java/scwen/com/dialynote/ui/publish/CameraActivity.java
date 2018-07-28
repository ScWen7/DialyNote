package scwen.com.dialynote.ui.publish;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;


import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import scwen.com.dialynote.R;
import scwen.com.dialynote.camera.JCameraView;
import scwen.com.dialynote.camera.listener.ClickListener;
import scwen.com.dialynote.camera.listener.ErrorListener;
import scwen.com.dialynote.camera.listener.JCameraListener;
import scwen.com.dialynote.camera.util.FileUtil;

public class CameraActivity extends AppCompatActivity {

    public static final int RESULTCODE_IMAGE = 102;
    public static final int RESULTCODE_VIDEO = 103;
    public static final String PARAM_FILE_PATH = "file_path";
    public static final String PARAM_VIDEO_THUMB_PATH = "thumb_path";


    @BindView(R.id.jcamera_view)
    JCameraView jCameraView;

    public static final String CAMERA_MODE = "camera_mode";

    private int cameraMode = JCameraView.BUTTON_STATE_BOTH;

    public static void start(Activity context, int requestCode, int cameraMode) {
        Intent intent = new Intent(context, CameraActivity.class);
        intent.putExtra(CAMERA_MODE, cameraMode);
        context.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_camera);
        ButterKnife.bind(this);

        cameraMode = getIntent().getIntExtra(CAMERA_MODE, JCameraView.BUTTON_STATE_BOTH);
        //设置视频保存路径
        jCameraView.setSaveVideoPath(Environment.getExternalStorageDirectory().getPath() + File.separator + "ArtBloger");
        jCameraView.setFeatures(cameraMode);
        String tip = "";
        switch (cameraMode) {
            case JCameraView.BUTTON_STATE_BOTH:
                tip = "轻触拍照  按住拍摄";
                break;
            case JCameraView.BUTTON_STATE_ONLY_CAPTURE:
                tip = "当前仅支持拍照";
                break;
            case JCameraView.BUTTON_STATE_ONLY_RECORDER:
                tip = "按住拍摄";
                break;
        }
        jCameraView.setTip(tip);
        jCameraView.setMediaQuality(JCameraView.MEDIA_QUALITY_HIGH);
        jCameraView.setErrorLisenter(new ErrorListener() {
            @Override
            public void onError() {
                //错误监听
                Log.i("CJT", "camera error");
                Toast.makeText(CameraActivity.this, "请检查相机权限~", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void AudioPermissionError() {
                Toast.makeText(CameraActivity.this, "请检查录音权限~", Toast.LENGTH_SHORT).show();
            }
        });
        //JCameraView监听
        jCameraView.setJCameraLisenter(new JCameraListener() {
            @Override
            public void captureSuccess(Bitmap bitmap) {
                //获取图片bitmap
//                Log.i("JCameraView", "bitmap = " + bitmap.getWidth());
                String path = FileUtil.saveBitmap("ArtBloger", bitmap);
                Intent intent = new Intent();
                intent.putExtra(PARAM_FILE_PATH, path);
                setResult(RESULTCODE_IMAGE, intent);
                finish();
            }

            @Override
            public void recordSuccess(String url, Bitmap firstFrame) {
                //获取视频路径
//                String path = FileUtil.saveBitmap("JCamera", firstFrame);
                Intent intent = new Intent();
                intent.putExtra(PARAM_FILE_PATH, url);
//                intent.putExtra(PARAM_VIDEO_THUMB_PATH, path);
                setResult(RESULTCODE_VIDEO, intent);
                finish();
            }
        });

        jCameraView.setLeftClickListener(new ClickListener() {
            @Override
            public void onClick() {
                CameraActivity.this.finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //全屏显示
        if (Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        } else {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(option);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        jCameraView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        jCameraView.onPause();
    }

}
