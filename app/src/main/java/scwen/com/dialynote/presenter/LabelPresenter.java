package scwen.com.dialynote.presenter;

import scwen.com.dialynote.appbase.BasePresenter;
import scwen.com.dialynote.contract.LabelContract;
import scwen.com.dialynote.model.LabelModel;

/**
 * Created by scwen on 2018/7/25.
 * QQ ：811733738
 * 作用：
 */

public class LabelPresenter extends BasePresenter<LabelModel, LabelContract.LabelView> implements LabelContract.LabelPresenter {
    public LabelPresenter(LabelContract.LabelView view) {
        super(view);
    }

    @Override
    public void getLabelList() {

    }

    @Override
    protected LabelModel createModel() {
        return null;
    }
}
