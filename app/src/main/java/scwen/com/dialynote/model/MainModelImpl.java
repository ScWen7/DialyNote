package scwen.com.dialynote.model;

import org.litepal.LitePal;

import java.util.List;

import io.reactivex.Observable;
import scwen.com.dialynote.contract.MainContract;
import scwen.com.dialynote.domain.TopicBean;

/**
 * Created by scwen on 2018/7/27.
 * QQ ：811733738
 * 作用：
 */

public class MainModelImpl implements MainContract.MainModel {
    @Override
    public Observable<List<TopicBean>> provideAllTopics() {

        return Observable.just(LitePal.findAll(TopicBean.class));
    }
}
