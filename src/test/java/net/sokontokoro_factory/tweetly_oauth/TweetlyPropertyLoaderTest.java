package net.sokontokoro_factory.tweetly_oauth;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * Created by TATSUYA-PC4 on 2016/04/13.
 */
public class TweetlyPropertyLoaderTest {

    @Test
    public void getConsumerKey_case1_設定ファイルからoauth_consumer_keyのvalueを読み込める() throws Exception {
        // case1: 設定ファイルからoauth_consumer_keyのvalueを読み込める

        String expect = "test_key";
        String actual = TweetlyPropertyLoader.getConsumerKey();

        assertThat(actual, is(expect));

    }

    @Test
    public void getConsumerSecret_case1_設定ファイルからoauth_consumer_secretのvalueを読み込める() throws Exception {
        // case1: 設定ファイルからoauth_consumer_secretのvalueを読み込める

        String expect = "test_secret";
        String actual = TweetlyPropertyLoader.getConsumerSecret();

        assertThat(actual, is(expect));

    }
}