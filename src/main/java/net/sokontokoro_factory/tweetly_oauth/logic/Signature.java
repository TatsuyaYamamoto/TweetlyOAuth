package net.sokontokoro_factory.tweetly_oauth.logic;

import net.sokontokoro_factory.tweetly_oauth.TweetlyOAuthException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Signature {
    private static final Logger logger = Logger.getLogger(Signature.class.getName());

    private static final String URL_ENCODE_NAME = "UTF-8";
    private static final String ALGORITHM = "HmacSHA1";

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
            TreeMap<String,String> element){

        /* logging */
        Object[] logParams = {consumer_secret, oauth_token_secret, requestMethod, requestURL, element};
        logger.entering(Signature.class.getSimpleName(), "generate", logParams);

        /* create key and data */
        String key = createKey(consumer_secret, oauth_token_secret);
        String data = createData(requestMethod, requestURL, element);

        /* calc. */
        try {
            return calculateSignature(data.toString(), key);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
            throw new TweetlyOAuthException(TweetlyOAuthException.SIGNATURE_ERROR, "通信用署名の作成に失敗しました。");
        }
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
            TreeMap<String,String> element){

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
            data.append(URLEncoder.encode(requestMethod, URL_ENCODE_NAME));
            data.append("&");
            data.append(URLEncoder.encode(requestURL, URL_ENCODE_NAME));
            data.append("&");
            data.append(URLEncoder.encode(parameter.toString(), URL_ENCODE_NAME));
        } catch (UnsupportedEncodingException e) {
            throw new TweetlyOAuthException(TweetlyOAuthException.INTERNALL_ERROR, e.getMessage());
        }

        return data.toString();
    }

    /**
     * dataとkeyからsignatureを作成する
     * @param data
     * @param key
     * @return
     */
    private static String calculateSignature(String data, String key) throws NoSuchAlgorithmException, InvalidKeyException {

        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
        Mac mac = Mac.getInstance(ALGORITHM);
        mac.init(secretKey);
        byte[] signature_sha1 = mac.doFinal(data.getBytes());

        String signature_base64 = DatatypeConverter.printBase64Binary(signature_sha1);
        String signature = null;
        try {
            signature = URLEncoder.encode(signature_base64, URL_ENCODE_NAME);
        } catch (UnsupportedEncodingException e) {
            throw new TweetlyOAuthException(TweetlyOAuthException.INTERNALL_ERROR, e.getMessage());
        }

        return signature;
    }
}