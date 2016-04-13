package net.sokontokoro_factory.tweetly_oauth.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * リクエストトークン取得によるもの
 * リクエストトークン。ユーザーが認証画面にアクセスする時のパラメータに必要な値です。
 *
 * Created by TATSUYA-PC4 on 2016/04/09.
 */
public class RequestToken extends AbstructToken{

    /**
     *
     */
    @Getter
    @Setter
    private String oauthCallbackConfirmed;
}
