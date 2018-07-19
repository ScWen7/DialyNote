package scwen.com.dialynote.retrofitmodle.rxhelper;



import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;
import scwen.com.dialynote.retrofitmodle.BaseResult;
import scwen.com.dialynote.retrofitmodle.exception.BaseException;

/**
 * Created by 解晓辉  on 2017/6/4 13:56 *
 * QQ  ：811733738
 * 作用: 对Retrofit 返回数据的预处理
 */

public class RxResultCompat {
    public static <T> ObservableTransformer<BaseResult<T>, T> handleResult() {
        return new ObservableTransformer<BaseResult<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<BaseResult<T>> upstream) {
                return upstream.flatMap(new Function<BaseResult<T>, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(BaseResult<T> tBaseResult) throws Exception {
                        int status = tBaseResult.getStatus();
                        tBaseResult.getStatus();
                        if (tBaseResult.isSuccess()) {
                            return Observable.just(tBaseResult.getData());
                        } else if (status == BaseResult.ERROR) {
                            return Observable.error(new BaseException(tBaseResult.getStatus() + "", tBaseResult.getInfo()));
                        }
                        return Observable.empty();
                    }
                });
            }
        };
    }
}
