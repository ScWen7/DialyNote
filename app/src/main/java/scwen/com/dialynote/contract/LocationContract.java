package scwen.com.dialynote.contract;

import android.graphics.pdf.PdfDocument;

import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiSearch;

import java.util.List;

import scwen.com.dialynote.appbase.BaseView;
import scwen.com.dialynote.event.LocationEvent;

/**
 * Created by scwen on 2018/7/25.
 * QQ ：811733738
 * 作用：
 */

public interface LocationContract {
    interface LocationView extends BaseView {

        void showLoading();


        void dismissLoading();


        void showLocationList(List<PoiItem> poiItems, int page);

        void locationFailed();
    }

    interface LocationPresenter {
        void getLocationList(int page);

        void getLocationList(String keyword, int page);
    }

    interface LocationModel {

    }
}
