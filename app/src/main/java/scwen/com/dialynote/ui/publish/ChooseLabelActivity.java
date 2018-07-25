package scwen.com.dialynote.ui.publish;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import scwen.com.dialynote.R;
import scwen.com.dialynote.appbase.BaseMvpActivity;
import scwen.com.dialynote.contract.LabelContract;
import scwen.com.dialynote.domain.LabelBean;
import scwen.com.dialynote.presenter.LabelPresenter;

public class ChooseLabelActivity extends BaseMvpActivity<LabelPresenter> implements LabelContract.LabelView {


    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_choose_label;
    }

    @Override
    protected void initData() {

    }

    @Override
    public LabelPresenter createPresenter() {
        return new LabelPresenter(this);
    }

    @Override
    public void showLabelList(List<LabelBean> labelBeans) {

    }
}
