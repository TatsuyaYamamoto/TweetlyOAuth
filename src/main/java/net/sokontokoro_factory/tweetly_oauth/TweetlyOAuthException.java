package net.sokontokoro_factory.tweetly_oauth;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by TATSUYA-PC4 on 2016/04/09.
 */
public class TweetlyOAuthException extends Exception{

    /* field */
    @Getter
    private int code;
    @Getter
    private String message;

    /* error code */
    /* 4xx系 external error */
    public static int EXTERNAL_ERROR = 400;
    public static int EXTERNAL_ERROR_NO_PROPERTIES = 401;
    public static int EXTERNAL_ERROR_SIGNATURE = 402;

    /* 5xx internal error */
    public static int INTERNALL_ERROR = 500;
    public static int SIGNATURE_ERROR = 501;
    public static int TWITTER_CONNECTION_ERROR = 502;

    /* コンストラクタ */
    public TweetlyOAuthException(){
        code = INTERNALL_ERROR;
        message = "システムエラー";
    }
    public TweetlyOAuthException(int code, String message){
        this.code = code;
        this.message = message;
    }
}
