package scwen.com.dialynote.presenter;

import java.util.List;

import io.reactivex.functions.Consumer;
import scwen.com.dialynote.appbase.BasePresenter;
import scwen.com.dialynote.contract.MainContract;
import scwen.com.dialynote.domain.TopicBean;
import scwen.com.dialynote.model.MainModelImpl;
import scwen.com.dialynote.retrofitmodle.rxhelper.RxExceptionHandler;
import scwen.com.dialynote.retrofitmodle.rxhelper.RxSchedulerHepler;

/**
 * Created by scwen on 2018/7/27.
 * QQ ：811733738
 * 作用：
 */

public class MainPresenterImpl extends BasePresenter<MainContract.MainModel, MainContract.MainView> implements MainContract.MainPresenter {
    public MainPresenterImpl(MainContract.MainView view) {
        super(view);
    }

    @Override
    public void getAllTopics() {
        addRx(mModel.provideAllTopics()
                .compose(RxSchedulerHepler.<List<TopicBean>>io_main())
                .subscribe(new Consumer<List<TopicBean>>() {
                    @Override
                    public void accept(List<TopicBean> topicBeans) throws Exception {
                        mView.showTopicList(topicBeans);
                    }
                }, new RxExceptionHandler<Throwable>(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                })));

    }

    @Override
    protected MainModelImpl createModel() {
        return new MainModelImpl();
    }
}
