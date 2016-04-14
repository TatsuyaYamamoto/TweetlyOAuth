package net.sokontokoro_factory.tweetly_oauth.logic;

import net.sokontokoro_factory.tweetly_oauth.TweetlyOAuthException;
import org.junit.Test;

import java.util.TreeMap;

import java.lang.reflect.Method;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * Created by TATSUYA-PC4 on 2016/04/14.
 */
public class SignatureTest {

    @Test
    public void Signatureを作成出来る() throws Exception {

        String actualOauthConsumerKey = "pVJJ9XvhyBSNxS4765zK0qoRw";
        String actualConsumerSecret = "WvZMnCdn2p7jdmvuXJ9VBKz4dg9CqL7XUiprTeG0cQtaXFSTaW";

        String actualHttpMethod = "GET";
        String actualRequestEndpoint = "https://api.twitter.com/1.1/";
        String actualOauthNonce = "0b347258237d6abf90dcb68d2b491790";
        String actualSignatureMethod = "HMAC-SHA1";
        String actualOauthTimestamp = "1460640528";
        String actualOauthVersion = "1.0";

        String expectSignature = "02JOR4ND6z5vA0sC%2FGtv3woqt4k%3D";

        TreeMap<String, String> authorizationMap = new TreeMap<>();

        // required params
        authorizationMap.put("oauth_consumer_key",      actualOauthConsumerKey);
        authorizationMap.put("oauth_nonce",             actualOauthNonce);
        authorizationMap.put("oauth_signature_method",  actualSignatureMethod);
        authorizationMap.put("oauth_timestamp",         actualOauthTimestamp);
        authorizationMap.put("oauth_version",           actualOauthVersion);

        String signature = Signature.generate(
                actualConsumerSecret,
                "",
                actualHttpMethod,
                actualRequestEndpoint,
                authorizationMap
        );
        assertThat(signature, is(expectSignature));
    }

    @Test
    public void signature用のkeyを作成できる() throws Exception{
        String arg1 = "kotori";
        String arg2 = "chun";
        String expect = "kotori&chun";

        Signature signature = new Signature();
        Method method = Signature.class.getDeclaredMethod("createKey", String.class, String.class);
        method.setAccessible(true);
        String actual = (String)method.invoke(signature, arg1, arg2);

        assertThat(actual, is(expect));
    }

    @Test
    public void signature用のdataを作成できる() throws Exception{
        String requestMethod = "GET";
        String requestUrl = "https://api.twitter.com/1.1/";
        TreeMap<String,String> element = new TreeMap<>();
        element.put("bbb", "222");
        element.put("aaa", "111");

        String expect =
                "GET" +
                "&" +
                "https%3A%2F%2Fapi.twitter.com%2F1.1%2F" +
                "&" +
                "aaa%3D111%26bbb%3D222";

        Signature signature = new Signature();
        Method method = Signature.class.getDeclaredMethod("createData", String.class, String.class, TreeMap.class);
        method.setAccessible(true);
        String actual = (String)method.invoke(signature, requestMethod, requestUrl, element);

        assertThat(actual, is(expect));

    }

}