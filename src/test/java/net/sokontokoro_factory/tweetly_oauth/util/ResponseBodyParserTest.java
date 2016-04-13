package net.sokontokoro_factory.tweetly_oauth.util;

import net.sokontokoro_factory.tweetly_oauth.dto.AccessToken;
import net.sokontokoro_factory.tweetly_oauth.dto.RequestToken;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


/**
 * Created by TATSUYA-PC4 on 2016/04/10.
 */
public class ResponseBodyParserTest {

    @Test
    public void RequestTokenオブジェクトに変換する() throws Exception {
        String actualRequestToken = "hogehoge";
        String actualRequestTokenSecret = "chunchun";
        String actualCallbackConfirmed = "true";

        String actualStringRequestToken =
                "oauth_token="+actualRequestToken+
                "&oauth_token_secret="+actualRequestTokenSecret +
                "&oauth_callback_confirmed=" + actualCallbackConfirmed;

        RequestToken actualRequestTokenObject = ResponseBodyParser.toRequestToken(actualStringRequestToken);

        assertThat(actualRequestTokenObject. getToken(), is(actualRequestToken));
        assertThat(actualRequestTokenObject.getTokenSecret(), is(actualRequestTokenSecret));
        assertThat(actualRequestTokenObject.getOauthCallbackConfirmed(), is(actualCallbackConfirmed));
    }

    @Test
    public void AccessTokenオブジェクトに変換する() throws Exception {
        String actualAccessToken = "hogehoge";
        String actualAccessTokenSecret = "chunchun";
        String actualUserId = "12312387";
        String actualScreenName = "kotorichun";

        String actualStringAccessToken =
                "oauth_token="+actualAccessToken+
                "&oauth_token_secret="+actualAccessTokenSecret +
                "&user_id=" + actualUserId +
                "&screen_name=" + actualScreenName;

        AccessToken actualRequestTokenObject = ResponseBodyParser.toAccessToken(actualStringAccessToken);

        assertThat(actualRequestTokenObject.getToken(), is(actualAccessToken));
        assertThat(actualRequestTokenObject.getTokenSecret(), is(actualAccessTokenSecret));
        assertThat(actualRequestTokenObject.getUserId(), is(actualUserId));
        assertThat(actualRequestTokenObject.getScreenName(), is(actualScreenName));


    }
}