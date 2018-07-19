package scwen.com.dialynote.domain;

/**
 * @author yiw
 * @ClassName: User
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2015-12-28 下午3:45:04
 */
public class User {
    private String id;
    private String name;

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
