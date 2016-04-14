package net.sokontokoro_factory.tweetly_oauth.dto;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * Created by TATSUYA-PC4 on 2016/04/14.
 */
public class AccessTokenTest {

    @Test
    public void getterAndSetter(){
        AccessToken accessToken = new AccessToken();
        String actualScreenName = "sn";
        String actualUserId = "ui";
        String actualToken = "token";
        String actualTokenSrcret = "tokensecret";

        accessToken.setScreenName(actualScreenName);
        accessToken.setUserId(actualUserId);
        accessToken.setToken(actualToken);
        accessToken.setTokenSecret(actualTokenSrcret);

        assertThat(accessToken.getScreenName(), is(actualScreenName));
        assertThat(accessToken.getUserId(), is(actualUserId));
        assertThat(accessToken.getToken(), is(actualToken));
        assertThat(accessToken.getTokenSecret(), is(actualTokenSrcret));

    }

}