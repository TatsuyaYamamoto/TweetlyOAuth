package net.sokontokoro_factory.tweetly_oauth;

import net.sokontokoro_factory.tweetly_oauth.dto.AccessToken;
import net.sokontokoro_factory.tweetly_oauth.dto.RequestToken;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class TweetlyOAuthTest {
    private TweetlyOAuth targetClass;

    private static String ACCESS_TOKEN = "298062670-V47MZpkeszBx9aWEYmYRPTCTLjHFKWudAUNc7d05";
    private static String ACCESS_TOKEN_SECRET = "TUODpJhOSDajAortXlKDgaWK0NqgdkcqGrDGAcTbWJ3iy";
    private static String USER_ID = "298062670";

    @Test
    public void リクエストトークンを取得できる() throws Exception {
        /* リクエストトークンを取得する */
        RequestToken token = targetClass.getRequestToken("tmpUri");

        /* パースした結果をオブジェクトの変数として持っている */
        assertNotNull(token.getToken());
        assertNotNull(token.getTokenSecret());
        assertNotNull(token.getOauthCallbackConfirmed());
    }

    @Test
    public void ユーザープロファイル画像のURLを取得できる() throws Exception {
        /* アクセストークンを作成する */
        AccessToken token = new AccessToken();
        token.setToken(ACCESS_TOKEN);
        token.setTokenSecret(ACCESS_TOKEN_SECRET);
        token.setUserId(USER_ID);

        /* APIリクエスト。取得したJSONからプロファイル画像のURLを抜き出す */
        String actualBody = targetClass.getUsersShow(USER_ID, token);
        String profileImageUrl = new JSONObject(actualBody).get("profile_image_url").toString();

        /* assert */
        assertThat(profileImageUrl, is("http://pbs.twimg.com/profile_images/3144229927/1ebde74324e5fb5d91ec6aa9bc3024fa_normal.gif"));
    }

    @Before
    public void setup(){
        targetClass = new TweetlyOAuth();
    }
}