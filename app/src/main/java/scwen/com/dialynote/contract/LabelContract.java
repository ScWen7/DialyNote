package scwen.com.dialynote.contract;

import java.util.List;

import scwen.com.dialynote.appbase.BaseView;
import scwen.com.dialynote.domain.LabelBean;

/**
 * Created by scwen on 2018/7/25.
 * QQ ：811733738
 * 作用：
 */

public interface LabelContract {

    interface LabelView extends BaseView {

        void showLabelList(List<LabelBean> labelBeans);

    }

    interface LabelPresenter {
        void getLabelList();
    }
}
