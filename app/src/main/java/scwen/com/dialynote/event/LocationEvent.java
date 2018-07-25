package scwen.com.dialynote.event;

import android.os.Parcel;
import android.os.Parcelable;

import com.amap.api.location.AMapLocation;
import com.amap.api.services.core.PoiItem;

/**
 * Created by Administrator on 2017/10/14.
 */

public class LocationEvent implements Parcelable {
    //纬度
    private double latitude;
    //经度
    private double longitude;
    //省份
    private String province;
    // #city=上海市
    private String city;
    // #district=浦东新区
    private String district;
    // #cityCode=021
    private String cityCode;
    // #adCode=310115
    private String adCode;
    // #address=上海市浦东新区南泉北路靠近中融恒瑞国际大厦
    private String address;
    // #country=中国
    private String country;
    // #road=南泉北路
    private String road;
    // #poiName=中融恒瑞国际大厦
    private String poiName;
    // #street=南泉北路
    private String street;
    // #streetNum=549号
    private String streetNum;
    // #aoiName=中融恒瑞国际大厦
    private String aoiName;
    // #poiid=#floor=
    // #locationDetail=
    // #description=在中融恒瑞国际大厦附近
    private String description;
    // #locationType=1


    private long lastTime;

    public long getLastTime() {
        return lastTime;
    }

    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }

    /**
     * 响应码
     * 问题说明
     * 问题排查策略
     * 0
     * 定位成功。
     * 可以在定位回调里判断定位返回成功后再进行业务逻辑运算。
     * 1
     * 一些重要参数为空，如context；
     * 请对定位传递的参数进行非空判断。
     * 2
     * 定位失败，由于仅扫描到单个wifi，且没有基站信息。
     * 请重新尝试。
     * 3
     * 获取到的请求参数为空，可能获取过程中出现异常。
     * 请对所连接网络进行全面检查，请求可能被篡改。
     * 4
     * 请求服务器过程中的异常，多为网络情况差，链路不通导致
     * 请检查设备网络是否通畅，检查通过接口设置的网络访问超时时间，建议采用默认的30秒。
     * 5
     * 请求被恶意劫持，定位结果解析失败。
     * 您可以稍后再试，或检查网络链路是否存在异常。
     * 6
     * 定位服务返回定位失败。
     * 请获取errorDetail（通过getLocationDetail()方法获取）信息并参考定位常见问题进行解决。
     * 7
     * KEY鉴权失败。
     * 请仔细检查key绑定的sha1值与apk签名sha1值是否对应，或通过高频问题查找相关解决办法。
     * 8
     * Android exception常规错误
     * 请将errordetail（通过getLocationDetail()方法获取）信息通过工单系统反馈给我们。
     * 9
     * 定位初始化时出现异常。
     * 请重新启动定位。
     * 10
     * 定位客户端启动失败。
     * 请检查AndroidManifest.xml文件是否配置了APSService定位服务
     * 11
     * 定位时的基站信息错误。
     * 请检查是否安装SIM卡，设备很有可能连入了伪基站网络。
     * 12
     * 缺少定位权限。
     * 请在设备的设置中开启app的定位权限。
     * 13
     * 定位失败，由于未获得WIFI列表和基站信息，且GPS当前不可用。
     * 建议开启设备的WIFI模块，并将设备中插入一张可以正常工作的SIM卡，或者检查GPS是否开启；如果以上都内容都确认无误，请您检查App是否被授予定位权限。
     * 14
     * GPS 定位失败，由于设备当前 GPS 状态差。
     * 建议持设备到相对开阔的露天场所再次尝试。
     * 15
     * 定位结果被模拟导致定位失败
     * 如果您希望位置被模拟，请通过setMockEnable(true);方法开启允许位置模拟
     * 16
     * 当前POI检索条件、行政区划检索条件下，无可用地理围栏
     * 建议调整检索条件后重新尝试，例如调整POI关键字，调整POI类型，调整周边搜区域，调整行政区关键字等。
     * 18
     * 定位失败，由于手机WIFI功能被关闭同时设置为飞行模式
     * 建议手机关闭飞行模式，并打开WIFI开关
     * 19
     * 定位失败，由于手机没插sim卡且WIFI功能被关闭
     * 建议手机插上sim卡，打开WIFI开关
     */
    //定位状态 -2 正在定位| -1 未开始定位 |0 定位成功 ，其他失败
    private int status = -1;
    //正在定位
    public static final int STATE_DOING = -2;
    //定位未开始
    public static final int STATE_NONE = -1;
    //定位成功
    public static final int STATE_SUCCESS = 0;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LocationEvent(int status) {
        this.status = status;
    }

    public LocationEvent(PoiItem poiItem) {
        this.status = STATE_SUCCESS;
        this.latitude = poiItem.getLatLonPoint().getLatitude();
        this.longitude = poiItem.getLatLonPoint().getLongitude();
        this.province = poiItem.getProvinceName();
        this.city = poiItem.getCityName();
        this.district = poiItem.getDirection();
        this.cityCode = poiItem.getCityCode();
        this.adCode = poiItem.getAdCode();
        this.address = poiItem.getSnippet();
//        this.country = poiItem.get
////        this.road = poiItem.get;
        this.aoiName = poiItem.getTitle();
        this.poiName = poiItem.getTitle();
//        this.street = locationEvent.getStreet();
//        this.streetNum = locationEvent.getStreetNum();
//        this.aoiName = locationEvent.getAoiName();
//        this.description = locationEvent.getDescription();
    }

    public LocationEvent(LocationEvent locationEvent) {
        this.status = STATE_SUCCESS;
        this.latitude = locationEvent.getLatitude();
        this.longitude = locationEvent.getLongitude();
        this.province = locationEvent.getProvince();
        this.city = locationEvent.getCity();
        this.district = locationEvent.getDistrict();
        this.cityCode = locationEvent.getCityCode();
        this.adCode = locationEvent.getAdCode();
        this.address = locationEvent.getAddress();
        this.country = locationEvent.getCountry();
        this.road = locationEvent.getRoad();
        this.poiName = locationEvent.getPoiName();
        this.street = locationEvent.getStreet();
        this.streetNum = locationEvent.getStreetNum();
        this.aoiName = locationEvent.getAoiName();
        this.description = locationEvent.getDescription();
    }

    /**
     * @param latitude    纬度
     * @param longitude   经度
     * @param province    省份
     * @param city        城 市
     * @param district    城区
     * @param cityCode    城市编码
     * @param adCode      adcode
     * @param address     address
     * @param country     国家
     * @param road        路
     * @param poiName     poiname
     * @param street      街道
     * @param streetNum   门牌号
     * @param aoiName     aoiname
     * @param description 描述
     * @param errorMsg    错误码
     */
    public LocationEvent(double latitude, double longitude, String province, String city, String district, String cityCode, String adCode, String address, String country, String road, String poiName, String street, String streetNum, String aoiName, String description, String errorMsg) {
        this.status = STATE_SUCCESS;
        this.latitude = latitude;
        this.longitude = longitude;
        this.province = province;
        this.city = city;
        this.district = district;
        this.cityCode = cityCode;
        this.adCode = adCode;
        this.address = address;
        this.country = country;
        this.road = road;
        this.poiName = poiName;
        this.street = street;
        this.streetNum = streetNum;
        this.aoiName = aoiName;
        this.description = description;
    }

    public LocationEvent(AMapLocation aMapLocation) {
        latitude = aMapLocation.getLatitude();
        longitude = aMapLocation.getLongitude();
        province = aMapLocation.getProvince();
        city = aMapLocation.getCity();
        district = aMapLocation.getDistrict();
        cityCode = aMapLocation.getCityCode();
        adCode = aMapLocation.getAdCode();
        address = aMapLocation.getAddress();
        country = aMapLocation.getCountry();
        road = aMapLocation.getRoad();
        poiName = aMapLocation.getPoiName();
        street = aMapLocation.getStreet();
        streetNum = aMapLocation.getStreetNum();
        aoiName = aMapLocation.getAoiName();
        description = aMapLocation.getDescription();
        status = aMapLocation.getErrorCode();

    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getAdCode() {
        return adCode;
    }

    public void setAdCode(String adCode) {
        this.adCode = adCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public String getPoiName() {
        return poiName;
    }

    public void setPoiName(String poiName) {
        this.poiName = poiName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNum() {
        return streetNum;
    }

    public void setStreetNum(String streetNum) {
        this.streetNum = streetNum;
    }

    public String getAoiName() {
        return aoiName;
    }

    public void setAoiName(String aoiName) {
        this.aoiName = aoiName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
        dest.writeString(this.province);
        dest.writeString(this.city);
        dest.writeString(this.district);
        dest.writeString(this.cityCode);
        dest.writeString(this.adCode);
        dest.writeString(this.address);
        dest.writeString(this.country);
        dest.writeString(this.road);
        dest.writeString(this.poiName);
        dest.writeString(this.street);
        dest.writeString(this.streetNum);
        dest.writeString(this.aoiName);
        dest.writeString(this.description);
        dest.writeLong(this.lastTime);
        dest.writeInt(this.status);
    }

    protected LocationEvent(Parcel in) {
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.province = in.readString();
        this.city = in.readString();
        this.district = in.readString();
        this.cityCode = in.readString();
        this.adCode = in.readString();
        this.address = in.readString();
        this.country = in.readString();
        this.road = in.readString();
        this.poiName = in.readString();
        this.street = in.readString();
        this.streetNum = in.readString();
        this.aoiName = in.readString();
        this.description = in.readString();
        this.lastTime = in.readLong();
        this.status = in.readInt();
    }

    public static final Parcelable.Creator<LocationEvent> CREATOR = new Parcelable.Creator<LocationEvent>() {
        @Override
        public LocationEvent createFromParcel(Parcel source) {
            return new LocationEvent(source);
        }

        @Override
        public LocationEvent[] newArray(int size) {
            return new LocationEvent[size];
        }
    };
}
