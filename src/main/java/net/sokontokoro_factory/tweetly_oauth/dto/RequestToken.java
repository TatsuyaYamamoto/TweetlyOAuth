package net.sokontokoro_factory.tweetly_oauth.dto;

import lombok.Getter;
import lombok.Setter;

public class RequestToken extends AbstructToken{

    /**
     * ユーザーが認証画面にアクセスする際に使用するパラメータ
     */
    @Getter
    @Setter
    private String oauthCallbackConfirmed;
}
