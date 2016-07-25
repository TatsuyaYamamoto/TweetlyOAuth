package net.sokontokoro_factory.tweetly_oauth.logic;


import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * Created by TATSUYA-PC4 on 2016/04/14.
 */
public class AuthorizationTest {

    @Test
    public void リクエストに使用するauthorization文字列を取得できる() throws Exception {
        Authorization authorization = new Authorization(Authorization.Method.GET, "https://api.twitter.com/1.1/");
        String actual = authorization.get();
        String[] actual1 = actual.split(" ");

        // 半角空白をはさんで、２つの文字列から構成される
        assertThat(actual1.length, is(2));

        // ,で区切られたkey valueが6件ある
        String[] actual2 = actual1[1].split(",");
        assertThat(actual2.length, is(6));

        // 最初の1件はoauth_consumer_keyである
        String actual3 = actual2[0].split("=")[0];
        assertThat(actual3, is("oauth_consumer_key"));
    }
}