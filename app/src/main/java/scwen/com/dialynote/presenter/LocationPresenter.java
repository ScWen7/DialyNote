package scwen.com.dialynote.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;

import java.util.ArrayList;

import scwen.com.dialynote.appbase.BasePresenter;
import scwen.com.dialynote.appbase.MyApplication;
import scwen.com.dialynote.contract.LocationContract;
import scwen.com.dialynote.event.LocationEvent;
import scwen.com.dialynote.model.LocationModel;
import scwen.com.dialynote.ui.publish.ChooseLocationActivity;

/**
 * Created by scwen on 2018/7/25.
 * QQ ：811733738
 * 作用：
 */

public class LocationPresenter extends BasePresenter<LocationModel, LocationContract.LocationView> implements LocationContract.LocationPresenter {
    private LocationEvent mLocationEvent;

    private int page;

    private PoiSearch.Query mQuery;
    private AMapLocationClient mMLocationClient;


    private AMapLocationListener mAMapLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {

            if (aMapLocation != null) {
                Log.e("TAG", "定位：" + aMapLocation.getErrorCode());
                if (aMapLocation.getErrorCode() == 0) {
                    mLocationEvent = new LocationEvent(aMapLocation);
                    MyApplication mainApplication = MyApplication.getInstence();
                    mainApplication.setLocationEvent(mLocationEvent);
                    mLocationEvent.setLastTime(System.currentTimeMillis());
                    searchAddress(mLocationEvent, "", page, null);

                } else {
                    mView.locationFailed();
                    mView.dismissLoading();
                }
            }
        }
    };


    public LocationPresenter(LocationContract.LocationView view) {
        super(view);
    }

    @Override
    protected LocationModel createModel() {
        return null;
    }

    @Override
    public void getLocationList(int page) {

        this.page = page;

        MyApplication mainApplication = MyApplication.getInstence();
        LocationEvent locationEvent = mainApplication.getLocationEvent();
        if (locationEvent != null) {
            mLocationEvent = locationEvent;
            searchAddress(mLocationEvent, "", page, null);
        } else {
            locationAddress();
        }

    }

    @Override
    public void getLocationList(String keyword, int page) {
        this.page = page;

        MyApplication mainApplication = MyApplication.getInstence();
        LocationEvent locationEvent = mainApplication.getLocationEvent();
        if (locationEvent != null) {
            mLocationEvent = locationEvent;
            searchAddress(mLocationEvent, keyword, page, mLocationEvent.getCity());
        } else {
            locationAddress();
        }
    }


    private void searchAddress(LocationEvent locationEvent, String keyword, int pageNum, String city) {
        if (mQuery == null) {
            if (TextUtils.isEmpty(city)) {
                mQuery = new PoiSearch.Query(keyword, "");
            } else {
                mQuery = new PoiSearch.Query(keyword, "", city);
            }

            mQuery.setCityLimit(true);
            mQuery.setDistanceSort(true);
            mQuery.setPageSize(10);
        }
        mQuery.setPageNum(pageNum);

        PoiSearch poiSearch = new PoiSearch(mContext, mQuery);

        poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(locationEvent.getLatitude(), locationEvent.getLongitude()), 1000));

        poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
            @Override
            public void onPoiSearched(PoiResult poiResult, int i) {

                mView.dismissLoading();
                if (i == 1000) {

                    ArrayList<PoiItem> pois = poiResult.getPois();

                    mView.showLocationList(pois, page);

                } else {
                    mView.locationFailed();
                }


            }

            @Override
            public void onPoiItemSearched(PoiItem poiItem, int i) {
            }
        });
        poiSearch.searchPOIAsyn();
    }

    /**
     * 进行定位操作
     */
    private void locationAddress() {
        Log.e("TAG", "定位");
        mMLocationClient = new AMapLocationClient(MyApplication.getInstence());
        AMapLocationClientOption aMapLocationClientOption = new AMapLocationClientOption();
        aMapLocationClientOption.setOnceLocation(true);
        aMapLocationClientOption.setInterval(5000);
        aMapLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mMLocationClient.setLocationOption(aMapLocationClientOption);
        mMLocationClient.setLocationListener(mAMapLocationListener);
        mMLocationClient.startLocation();
    }

    @Override
    public void detachView() {
        super.detachView();
        mMLocationClient.stopLocation();
        mMLocationClient.unRegisterLocationListener(mAMapLocationListener);
        mAMapLocationListener = null;
        mQuery = null;
    }
}
