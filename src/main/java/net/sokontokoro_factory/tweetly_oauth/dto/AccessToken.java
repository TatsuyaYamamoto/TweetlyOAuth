package net.sokontokoro_factory.tweetly_oauth.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by TATSUYA-PC4 on 2016/04/09.
 */
public class AccessToken extends AbstructToken{

    /**
     *
     */
    @Getter
    @Setter
    private String userId;

    /**
     *
     */
    @Getter
    @Setter
    private String screenName;
}
