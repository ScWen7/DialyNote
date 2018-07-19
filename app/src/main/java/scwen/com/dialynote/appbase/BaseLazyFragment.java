package scwen.com.dialynote.appbase;

import android.util.Log;

/**
 * Created by xxh on 2018/7/19.
 */

public abstract class BaseLazyFragment extends BaseFragment {

    //Fragment的View加载完毕的标记
    private boolean isViewCreated;

    //Fragment对用户可见的标记
    private boolean isUIVisible;

    @Override
    protected void initData() {
        isViewCreated = true;
        lazy();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isUIVisible = true;
            lazy();
        } else {
            isUIVisible = false;
        }
    }

    private void lazy() {
        //这里进行双重标记判断,是因为setUserVisibleHint会多次回调,并且会在onCreateView执行前回调,必须确保onCreateView加载完毕且页面可见,才加载数据
        if (isViewCreated && isUIVisible) {
            //数据加载完毕,恢复标记,防止重复加载
            isViewCreated = false;
            isUIVisible = false;
            Log.e("TAG", "BaseLazyFragment  lazyLoad");
            lazyLoad();
        }
    }

    /**
     * fragment  懒加载  方式加载数据
     */
    protected abstract void lazyLoad();
}
