package net.sokontokoro_factory.tweetly_oauth.dto;

import lombok.Getter;
import lombok.Setter;

public class AccessToken extends AbstructToken{

    /**
     * 数字のみで表す一意のID
     */
    @Getter
    @Setter
    private String userId;

    /**
     * @ から始まる一意のname
     */
    @Getter
    @Setter
    private String screenName;
}
