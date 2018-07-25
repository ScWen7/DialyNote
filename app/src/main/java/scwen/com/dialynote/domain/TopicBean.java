package scwen.com.dialynote.domain;

import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by scwen on 2018/7/25.
 * QQ ：811733738
 * 作用：
 */

public class TopicBean extends LitePalSupport {
    private String content;
    private String place;
    private String atitude;
    private String longitude;

    private LabelBean labelBean;

    private int type;

    private List<String> images;

    private List<String> videos;

    public String getContent() {
        return content == null ? "" : content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPlace() {
        return place == null ? "" : place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getAtitude() {
        return atitude == null ? "" : atitude;
    }

    public void setAtitude(String atitude) {
        this.atitude = atitude;
    }

    public String getLongitude() {
        return longitude == null ? "" : longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public LabelBean getLabelBean() {
        return labelBean;
    }

    public void setLabelBean(LabelBean labelBean) {
        this.labelBean = labelBean;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<String> getImages() {
        if (images == null) {
            return new ArrayList<>();
        }
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<String> getVideos() {
        if (videos == null) {
            return new ArrayList<>();
        }
        return videos;
    }

    public void setVideos(List<String> videos) {
        this.videos = videos;
    }
}
