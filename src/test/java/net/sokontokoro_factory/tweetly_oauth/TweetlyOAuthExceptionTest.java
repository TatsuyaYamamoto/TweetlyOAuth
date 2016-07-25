package net.sokontokoro_factory.tweetly_oauth;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class TweetlyOAuthExceptionTest {

    @Test
    public void test_引数なしの場合デフォルトエラーコード(){
        TweetlyOAuthException exception = new TweetlyOAuthException();

        assertThat(exception.getCode(), is(TweetlyOAuthException.INTERNALL_ERROR));
    }

    @Test
    public void test_メッセージとエラーコードを指定できる(){
        int anyErrorCode = 111;
        String anyMessage = "hogehoge";

        TweetlyOAuthException exception = new TweetlyOAuthException(anyErrorCode, anyMessage);

        assertThat(exception.getCode(), is(anyErrorCode));
        assertThat(exception.getMessage(), is(anyMessage));
    }
}