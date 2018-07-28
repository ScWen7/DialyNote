package scwen.com.dialynote.appbase;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import org.litepal.LitePalApplication;

import scwen.com.dialynote.event.LocationEvent;
import scwen.com.dialynote.utils.UIUtils;

/**
 * Created by xxh on 2018/7/19.
 */

public class MyApplication extends LitePalApplication {


    private static MyApplication instence;

    LocationEvent mLocationEvent;

    public void setLocationEvent(LocationEvent locationEvent) {
        mLocationEvent = locationEvent;
    }

    public LocationEvent getLocationEvent() {
        if (mLocationEvent != null) {
            long lastTime = mLocationEvent.getLastTime();
            if (System.currentTimeMillis() - lastTime > (5 * 60 * 1000)) {
                mLocationEvent = null;
            }
        }
        return mLocationEvent;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }



    @Override
    public void onCreate() {
        super.onCreate();
        instence = this;
        UIUtils.init(this);
        
    }

    public static MyApplication getInstence() {
        return instence;
    }
}
