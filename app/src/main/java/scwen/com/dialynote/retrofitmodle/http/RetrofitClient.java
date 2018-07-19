package scwen.com.dialynote.retrofitmodle.http;

import android.content.Context;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import scwen.com.dialynote.retrofitmodle.ApiService;
import scwen.com.dialynote.retrofitmodle.Contacts;
import scwen.com.dialynote.retrofitmodle.cookie.CookieJarImpl;
import scwen.com.dialynote.retrofitmodle.cookie.store.PersistentCookieStore;
import scwen.com.dialynote.retrofitmodle.fastjsonconverter.FastJsonConvertFactory;

/**
 * Created by 解晓辉  on 2017/5/24 20:49 *
 * QQ  ：811733738
 * 作用:   提供 Retrofit 网络连接 ApiService
 *
 */

public class RetrofitClient {


    private static RetrofitClient instance;

    private OkHttpClient mOkHttpClient;


    private Context mContext;

    private Retrofit mRetrofit;

    private ApiService mApiService;

    private RetrofitClient(Context context) {
        this.mContext = context;
    }

    public static RetrofitClient getInstance(Context context) {
        if (instance == null) {
            instance = new RetrofitClient(context);
        }
        return instance;
    }


    private OkHttpClient provideOkHttpClient() {
        if (mOkHttpClient == null) {
            mOkHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .addInterceptor(new HttpParamInterceptor(mContext))
                    .cookieJar(new CookieJarImpl(new PersistentCookieStore(mContext)))
                    .build();
        }


        return mOkHttpClient;
    }


    private Retrofit provideRetrofit() {

        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(Contacts.BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(FastJsonConvertFactory.create())
                    .client(provideOkHttpClient()).build();
        }

        return mRetrofit;
    }

    public ApiService provideApiService() {
        if (mApiService == null) {

            mApiService = provideRetrofit().create(ApiService.class);
        }
        return mApiService;
    }
}
