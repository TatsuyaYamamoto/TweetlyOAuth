package net.sokontokoro_factory.tweetly_oauth;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by TATSUYA-PC4 on 2016/04/09.
 */
public class TweetlyOAuthException extends Exception{

    /* field */
    @Getter
    @Setter
    private int code;
    @Getter
    @Setter
    private String message;

    /* error code */
    /* 4xx系 external error */
    public static int EXTERNAL_ERROR = 400;
    public static int EXTERNAL_ERROR_NO_PROPERTIES = 401;
    public static int EXTERNAL_ERROR_SIGNATURE = 402;

    /* 5xx internal error */
    public static int INTERNALL_ERROR = 500;

    /* コンストラクタ */
    public TweetlyOAuthException(){
        code = EXTERNAL_ERROR;
        message = "システムエラー";
    }
    public TweetlyOAuthException(int code, String message){
        this.code = code;
        this.message = message;
    }
}
