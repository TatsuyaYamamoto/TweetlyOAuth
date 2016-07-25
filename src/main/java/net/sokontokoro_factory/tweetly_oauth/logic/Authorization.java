package net.sokontokoro_factory.tweetly_oauth.logic;

import lombok.Getter;
import lombok.Setter;
import net.sokontokoro_factory.tweetly_oauth.TweetlyOAuthException;
import net.sokontokoro_factory.tweetly_oauth.TweetlyPropertyLoader;
import net.sokontokoro_factory.tweetly_oauth.dto.AbstructToken;

import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

/**
 * Created by TATSUYA-PC4 on 2016/04/09.
 */
public class Authorization {

    public enum Method{
        GET, POST
    }

    private static final String OAUTH_SIGNATURE_METHOD = "HMAC-SHA1";
    private static final String OAUTH_VERSION = "1.0";

    /* required */
    // インスタンス作成時に代入される
    private String oauthConsumerKey;
    private String oauthConsumerSecret;
    private String oauthNonce;
    private String oauthSignatureMethod;
    private String oauthTimestamp;
    private String oauthVersion;
    private String method;
    private String endpoint;

    /* optional */
    @Getter
    @Setter
    private String oauthCallback;
    @Getter
    @Setter
    private AbstructToken token;
    @Getter
    @Setter
    private String oauthVerifier;
    @Getter
    @Setter
    private String oauthSignature;
    @Getter
    @Setter
    Map<String, String> queryParams;

    /**
     * コンストラクタ
     *
     * @param method
     * @param endpoint
     * @throws TweetlyOAuthException
     */
    public Authorization(Method method, String endpoint) throws TweetlyOAuthException {
        this.oauthConsumerKey = TweetlyPropertyLoader.getConsumerKey();
        this.oauthConsumerSecret = TweetlyPropertyLoader.getConsumerSecret();
        this.oauthNonce = getNonce();
        this.oauthSignatureMethod = OAUTH_SIGNATURE_METHOD;
        this.oauthTimestamp = getTimestamp();
        this.oauthVersion = OAUTH_VERSION;

        this.method = method.name();
        this.endpoint = endpoint;
    }

    /**
     * api リクエストに使用するauthorization文字列を取得する
     *
     * @return
     */
    public String get() throws TweetlyOAuthException {

        TreeMap<String, String> authorizationMap = new TreeMap<>();

        // required params
        authorizationMap.put("oauth_consumer_key",      this.oauthConsumerKey);
        authorizationMap.put("oauth_nonce",             this.oauthNonce);
        authorizationMap.put("oauth_signature_method",  this.oauthSignatureMethod);
        authorizationMap.put("oauth_timestamp",         this.oauthTimestamp);
        authorizationMap.put("oauth_version",           this.oauthVersion);

        // optional params
        if(oauthCallback != null)   authorizationMap.put("oauth_callback",  this.oauthCallback);
        if(token != null)           authorizationMap.put("oauth_token",     this.token.getToken());
        if(oauthVerifier != null)   authorizationMap.put("oauth_verifier",  this.oauthVerifier);
        if(queryParams != null && queryParams.size() != 0) authorizationMap.putAll(queryParams);

        String signature = Signature.generate(
                this.oauthConsumerSecret,
                token != null? token.getTokenSecret(): "",  // oauth_token_secret
                this.method,
                this.endpoint,
                authorizationMap);
        authorizationMap.put("oauth_signature", signature);

        return getRequestHeaderAuthorization(authorizationMap);
    }

    /**
     * twitter apiアクセス用の request headerを作成する
     * @param authorizationMap
     * @return
     */
    private static String getRequestHeaderAuthorization(TreeMap<String,String> authorizationMap){
        StringBuffer authorizationString = new StringBuffer();
        int i = 0;
        for(String key: authorizationMap.keySet()){
            if(i==0){
                authorizationString.append("OAuth ");
            }else{
                authorizationString.append(",");
            }
            authorizationString.append(key);
            authorizationString.append("=");
            authorizationString.append(authorizationMap.get(key));
            i++;
        }

        return authorizationString.toString();
    }


    private static String getTimestamp(){
        return Long.toString(System.currentTimeMillis() / 1000);
    }

    private static String getNonce(){
        return UUID.randomUUID().toString();
    }
}