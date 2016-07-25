package net.sokontokoro_factory.tweetly_oauth.dto;

import lombok.Getter;
import lombok.Setter;

public abstract class AbstructToken{

    /**
     * レスポンスボディ内のkeyは、oauth_token
     */
    @Getter
    @Setter
    private String token;

    /**
     * レスポンスボディ内のkeyは、oauth_token_secret
     */
    @Getter
    @Setter
    private String tokenSecret;

}
