package scwen.com.dialynote.retrofitmodle;



import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import scwen.com.dialynote.domain.ReplyUser;

/**
 * Created by 解晓辉 on 2017/6/26.
 * 作用：
 */

public interface ApiService {


    @POST("memberLogin")
    Observable<BaseResult<String>> login(@Body ReplyUser user);


    @POST("getMemberInfo")
    Observable<BaseResult<String>> getMemberInfo();

    @POST("getCateProduct")
    Observable<BaseResult<String>> getCateProduct();


    @POST("memberLogout")
    Observable<BaseResult<String>> logout();

    @Streaming
    @GET
    Call<ResponseBody> downloadFile(@Url String fileUrl);



    @GET
    @Streaming
    Flowable<Response<ResponseBody>> download(@Url String url);

    @GET
    @Streaming
    Flowable<ResponseBody> download2(@Url String url);


}
