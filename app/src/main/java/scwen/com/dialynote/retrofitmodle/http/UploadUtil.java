package scwen.com.dialynote.retrofitmodle.http;

import android.content.Context;


import java.io.File;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import scwen.com.dialynote.retrofitmodle.BaseResult;
import scwen.com.dialynote.retrofitmodle.Contacts;
import scwen.com.dialynote.retrofitmodle.cookie.CookieJarImpl;
import scwen.com.dialynote.retrofitmodle.cookie.store.PersistentCookieStore;
import scwen.com.dialynote.retrofitmodle.fastjsonconverter.FastJsonConvertFactory;
import scwen.com.dialynote.retrofitmodle.rxhelper.RxSchedulerHepler;

/**
 * Created by 解晓辉 on 2017/6/28.
 * 作用:
 * 使用Retrofit 的上传工具类
 */

public class UploadUtil {
    public static Observable<BaseResult<String>> upload(Context context, String json, String filePath) {

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .cookieJar(new CookieJarImpl(new PersistentCookieStore(context)))
                .build();


        Retrofit retrofit = new Retrofit.Builder().baseUrl(Contacts.BASE_URL)
                .addConverterFactory(FastJsonConvertFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient)
                .build();

        UploadService uploadService = retrofit.create(UploadService.class);


        // 创建 RequestBody，用于封装 请求RequestBody
        File imageFile = new File(filePath);
        RequestBody requestFile =
                RequestBody.create(guessMimeType(imageFile.getName()), imageFile);




        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)//表单类型 "multipart/form-data"
                .addFormDataPart("file", imageFile.getName(), requestFile);

        List<MultipartBody.Part> parts = builder.build().parts();

        return uploadService.upload(parts)
                .compose(RxSchedulerHepler.<BaseResult<String>>io_main());

    }

    interface UploadService {
        @Multipart
        @POST("uploadImg")
        Observable<BaseResult<String>> upload(
                @Part List<MultipartBody.Part> partList);
    }

    private static MediaType guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        path = path.replace("#", "");   //解决文件名中含有#号异常的问题
        String contentType = fileNameMap.getContentTypeFor(path);
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return MediaType.parse(contentType);
    }

}
