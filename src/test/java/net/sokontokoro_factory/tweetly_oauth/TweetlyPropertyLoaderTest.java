package net.sokontokoro_factory.tweetly_oauth;

import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * Created by TATSUYA-PC4 on 2016/04/13.
 */
public class TweetlyPropertyLoaderTest {

    @Test
    public void 設定ファイルからoauth_consumer_keyのvalueを読み込める() throws Exception {
        String expect = "pVJJ9XvhyBSNxS4765zK0qoRw";
        String actual = TweetlyPropertyLoader.getConsumerKey();

        assertThat(actual, is(expect));
    }

    @Test
    public void 設定ファイルからoauth_consumer_secretのvalueを読み込める() throws Exception {
        String expect = "WvZMnCdn2p7jdmvuXJ9VBKz4dg9CqL7XUiprTeG0cQtaXFSTaW";
        String actual = TweetlyPropertyLoader.getConsumerSecret();

        assertThat(actual, is(expect));
    }

    @Test
    public void 設定ファイルが存在しない場合例外を出す() throws Exception {
        // PROPERTIES_FILE_NAMEを書き換えて読み込むpathを変更する
        TweetlyPropertyLoader tweetlyPropertyLoader = new TweetlyPropertyLoader();
        Class c = tweetlyPropertyLoader.getClass();
        Field field = c.getDeclaredField("PROPERTIES_FILE_NAME");
        field.setAccessible(true);
        String backup = (String)field.get(tweetlyPropertyLoader);

        // path変更
        field.set(tweetlyPropertyLoader, "tmp.properties");

        try{
            TweetlyPropertyLoader.getConsumerSecret();
            fail();
        }catch(TweetlyOAuthException e){
            // expect
        }finally {
            field.set(tweetlyPropertyLoader, backup);
        }
    }
}