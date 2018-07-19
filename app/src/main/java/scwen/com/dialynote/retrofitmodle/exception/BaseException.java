package scwen.com.dialynote.retrofitmodle.exception;

/**
 * Created by 解晓辉  on 2017/6/10 19:01 *
 * QQ  ：811733738
 * 作用:
 */

public class BaseException extends Exception {
    private String code;
    private String displayMessage;


    public BaseException(String code, String displayMessage) {
        this.code = code;
        this.displayMessage = displayMessage;
    }

    public BaseException(String message, String code, String displayMessage) {
        super(message);
        this.code = code;
        this.displayMessage = displayMessage;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDisplayMessage() {
        return displayMessage;
    }

    public void setDisplayMessage(String displayMessage) {
        this.displayMessage = displayMessage;
    }
}
