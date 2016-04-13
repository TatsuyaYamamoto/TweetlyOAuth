package net.sokontokoro_factory.tweetly_oauth.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by TATSUYA-PC4 on 2016/04/09.
 */
public abstract class AbstructToken{

    /**
     * レスポンスボディでは、oauth_token
     */
    @Getter
    @Setter
    private String token;

    /**
     * レスポンスボディでは、oauth_token_secret
     */
    @Getter
    @Setter
    private String tokenSecret;

}
