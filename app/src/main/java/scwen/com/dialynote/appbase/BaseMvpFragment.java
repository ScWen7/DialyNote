package scwen.com.dialynote.appbase;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by 解晓辉  on 2017/6/10 14:52 *
 * QQ  ：811733738
 * 作用:   封装完成之后方法的调用顺序为:
 *   getLayoutId()   -> initData()  -> createPresenter() -> intiData()
 *
 *   生命周期  onDestoryView -> 中包含  presenter 的 detachView 和 presenter的置空
 *
 *
 */

public abstract class BaseMvpFragment <P  extends BasePresenter> extends BaseFragment {


    protected  P mPresenter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mPresenter = createPresenter();
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(mPresenter!=null) {
            mPresenter.detachView();
            mPresenter = null;
        }

    }


    /**
     * 创建 Presenter
     * @return
     */
    public  abstract  P createPresenter() ;

}
