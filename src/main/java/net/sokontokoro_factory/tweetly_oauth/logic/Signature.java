package net.sokontokoro_factory.tweetly_oauth.logic;

import net.sokontokoro_factory.tweetly_oauth.TweetlyOAuthException;
import net.sokontokoro_factory.tweetly_oauth.util.Calculation;

import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.TreeMap;

/**
 * Created by TATSUYA-PC4 on 2016/04/09.
 */
public class Signature {
    /**
     *
     * @param consumer_secret
     * @param oauth_token_secret token未取得時は空文字列("")を指定すること
     * @param requestMethod
     * @param requestURL
     * @param element
     * @return
     */
    public static String generate(
            String consumer_secret,
            String oauth_token_secret,
            String requestMethod,
            String requestURL,
            TreeMap<String,String> element) throws TweetlyOAuthException {

        String key = createKey(consumer_secret, oauth_token_secret);
        String data = createData(requestMethod, requestURL, element);
        return calculateSignature(data.toString(), key);
    }

    /**
     * signature用のkeyを作成する
     * @param consumer_secret
     * @param oauth_token_secret
     * @return
     */
    private static String createKey(String consumer_secret, String oauth_token_secret){
        return consumer_secret + "&" + oauth_token_secret;
    }

    /**
     * signature用のdata(Signature base string)を作成する
     * @param requestMethod
     * @param requestURL
     * @param element
     * @return
     */
    private static String createData(
            String requestMethod,
            String requestURL,
            TreeMap<String,String> element) throws TweetlyOAuthException {

        StringBuffer parameter = new StringBuffer();
        int i = 0;
        for(String key: element.keySet()){
            if(i!=0) parameter.append("&");
            parameter.append(key);
            parameter.append("=");
            parameter.append(element.get(key));
            i++;
        }
        StringBuffer data = new StringBuffer();
        try {
            data.append(URLEncoder.encode(requestMethod, "UTF-8"));
            data.append("&");
            data.append(URLEncoder.encode(requestURL, "UTF-8"));
            data.append("&");
            data.append(URLEncoder.encode(parameter.toString(), "UTF-8"));
        } catch (Exception e) {}

        return data.toString();
    }

    /**
     * dataとkeyからsignatureを作成する
     * @param data
     * @param key
     * @return
     */
    private static String calculateSignature(String data, String key) throws TweetlyOAuthException {
        byte[] signature_sha1 = null;
        String signature = null;
        try {
            signature_sha1 = Calculation.calcHmacSHA1(data, key);
//            String signature_base64 = Base64.getEncoder().encodeToString(signature_sha1);
            String signature_base64 = DatatypeConverter.printBase64Binary(signature_sha1);
            signature = URLEncoder.encode(signature_base64, "UTF-8");
        } catch (InvalidKeyException | NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new TweetlyOAuthException();
        }
        return signature;
    }
}
