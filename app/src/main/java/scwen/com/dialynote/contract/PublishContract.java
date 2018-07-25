package scwen.com.dialynote.contract;

import scwen.com.dialynote.appbase.BaseView;

/**
 * Created by scwen on 2018/7/24.
 * QQ ：811733738
 * 作用：
 */

public interface PublishContract {
    interface PublishView extends BaseView {

        void showLoading();


        void dismissLoading();

    }

    interface PublishPresenter {

        void save();

    }

    interface PublishModel {

    }
}
