package net.sokontokoro_factory.tweetly_oauth.network;

import net.sokontokoro_factory.tweetly_oauth.dto.AccessToken;
import net.sokontokoro_factory.tweetly_oauth.dto.RequestToken;

/**
 * twitter service provider から取得したレスポンスボディをtoken dtoオブジェクトに変換する
 *
 * Created by TATSUYA-PC4 on 2016/04/09.
 */
public class ResponseBodyParser {

    /**
     * RequestTokenオブジェクトに変換する
     * key & value of "oauth_token", "oauth_token_secret" and "oauth_callback_confirmed" in responseBody
     *
     * @param stringRequestToken 取得したrequest Tokenを含むレスポンスボディの文字列
     * @return
     */
    public static RequestToken toRequestToken(String stringRequestToken){

        // &を=に置き換えて、取得文字列を各要素に分割
        String paramaters = stringRequestToken.replaceAll("&", "=");
        String[] paramater = paramaters.split("=");

        RequestToken requestToken = new RequestToken();
        requestToken.setToken(paramater[1]);
        requestToken.setTokenSecret(paramater[3]);
        requestToken.setOauthCallbackConfirmed(paramater[5]);

        return requestToken;
    }

    /**
     * AccessTokenオブジェクトに変換する
     * key & value of "oauth_token", "oauth_token_secret", "user_id" and "screen_name" in responseBody
     *
     * @param stringAccessToken 取得したaccessTokenを含むレスポンスボディの文字列
     * @return
     */
    public static AccessToken toAccessToken(String stringAccessToken){
        // &を=に置き換えて、取得文字列を各要素に分割
        String paramaters = stringAccessToken.replaceAll("&", "=");
        String[] paramater = paramaters.split("=");

        AccessToken accessToken = new AccessToken();
        accessToken.setToken(paramater[1]);
        accessToken.setTokenSecret(paramater[3]);
        accessToken.setUserId(paramater[5]);
        accessToken.setScreenName(paramater[7]);

		return accessToken;
    }
}
