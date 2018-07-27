package scwen.com.dialynote.contract;

import java.util.List;

import io.reactivex.Observable;
import scwen.com.dialynote.appbase.BaseView;
import scwen.com.dialynote.domain.TopicBean;

/**
 * Created by xxh on 2018/7/19.
 */

public interface MainContract {

    interface MainView extends BaseView {

        void showLoading();


        void dismissLoading();

        void showTopicList(List<TopicBean> topicList);
    }

    interface MainPresenter {
        void getAllTopics();
    }

    interface MainModel {
        Observable<List<TopicBean>> provideAllTopics();
    }

}
