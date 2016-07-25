package net.sokontokoro_factory.tweetly_oauth.dto;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * Created by TATSUYA-PC4 on 2016/04/15.
 */
public class RequestTokenTest {
    @Test
    public void getterAndSetter(){
        RequestToken requestToken = new RequestToken();
        String actualOauthCallbackConfirmed = "sn";
        String actualToken = "token";
        String actualTokenSrcret = "tokensecret";

        requestToken.setOauthCallbackConfirmed(actualOauthCallbackConfirmed);
        requestToken.setToken(actualToken);
        requestToken.setTokenSecret(actualTokenSrcret);

        assertThat(requestToken.getOauthCallbackConfirmed(), is(actualOauthCallbackConfirmed));
        assertThat(requestToken.getToken(), is(actualToken));
        assertThat(requestToken.getTokenSecret(), is(actualTokenSrcret));
    }
}