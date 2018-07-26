package scwen.com.dialynote.ui.publish;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.VideoView;

import scwen.com.dialynote.R;

public class VideoPreviewActivity extends AppCompatActivity {


    public static final String PATH = "path";

    public static void start(Context context, String path) {
        Intent intent = new Intent(context, VideoPreviewActivity.class);
        intent.putExtra(PATH, path);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_preview);

        VideoView video_view = findViewById(R.id.video_view);
        String s = getIntent().getStringExtra(PATH);
        video_view.setVideoPath(s);

    }
}
