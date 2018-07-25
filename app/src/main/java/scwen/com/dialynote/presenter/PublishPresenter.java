package scwen.com.dialynote.presenter;

import scwen.com.dialynote.appbase.BasePresenter;
import scwen.com.dialynote.contract.PublishContract;
import scwen.com.dialynote.model.PublishModel;

/**
 * Created by scwen on 2018/7/24.
 * QQ ：811733738
 * 作用：
 */

public class PublishPresenter extends BasePresenter<PublishModel, PublishContract.PublishView> {

    public PublishPresenter(PublishContract.PublishView view) {
        super(view);
    }

    @Override
    protected PublishModel createModel() {
        return null;
    }
}
