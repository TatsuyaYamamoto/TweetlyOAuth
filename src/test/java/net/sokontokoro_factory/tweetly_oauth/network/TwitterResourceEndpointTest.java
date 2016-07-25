package net.sokontokoro_factory.tweetly_oauth.network;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * Created by TATSUYA-PC4 on 2016/04/15.
 */
public class TwitterResourceEndpointTest {
    @Test
    public void testEnumTwitterResourceEndpoint(){
        assertThat(TwitterResourceEndpoint.REQUEST_TOKEN.getString(), is("https://api.twitter.com/oauth/request_token"));
        assertThat(TwitterResourceEndpoint.ACCESS_TOKEN.getString(), is("https://api.twitter.com/oauth/access_token"));
        assertThat(TwitterResourceEndpoint.USERS_SHOW.getString(), is("https://api.twitter.com/1.1/users/show.json"));
    }
}