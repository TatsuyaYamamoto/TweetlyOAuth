package net.sokontokoro_factory.tweetly_oauth;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by TATSUYA-PC4 on 2016/04/09.
 */
public class TweetlyPropertyLoader {

    private static String PROPERTIES_FILE_NAME = "tweetlyOauth.properties";

    private static String CONSUMER_KEY_PROPERTY_KEY = "oauth_consumer_key";
    private static String CONSUMER_SECRET_PROPERTY_KEY = "oauth_consumer_secret";


    /**
     * consumer key を取得する
     *
     * @return
     * @throws TweetlyOAuthException    ファイルがない場合
     */
    public static String getConsumerKey(){
        try{
            return loadFile().getProperty(CONSUMER_KEY_PROPERTY_KEY);
        }catch(NullPointerException e){
            e.printStackTrace();
            throw  new TweetlyOAuthException(TweetlyOAuthException.EXTERNAL_ERROR_NO_PROPERTIES, "プロパティファイルが見つかりませんでした");

        }catch(IOException e){
            e.printStackTrace();
            throw  new TweetlyOAuthException(TweetlyOAuthException.INTERNALL_ERROR, "プロパティファイルを読み込めませんでした");

        }
    }

    /**
     * consumer secret を取得する
     *
     * @return
     * @throws TweetlyOAuthException    ファイルがない場合
     */
    public static String getConsumerSecret(){
        try{
            return loadFile().getProperty(CONSUMER_SECRET_PROPERTY_KEY);
        }catch(NullPointerException e){
            e.printStackTrace();
            throw  new TweetlyOAuthException(TweetlyOAuthException.EXTERNAL_ERROR_NO_PROPERTIES, "プロパティファイルが見つかりませんでした");

        }catch(IOException e){
            e.printStackTrace();
            throw  new TweetlyOAuthException(TweetlyOAuthException.INTERNALL_ERROR, "プロパティファイルを読み込めませんでした");
        }
    }

    /**
     * propertiesファイルを読み込む
     *
     * @return
     * @throws IOException
     * @throws NullPointerException
     */
    private static Properties loadFile() throws IOException, NullPointerException {

        Properties properties = new Properties();
        String filePath = TweetlyPropertyLoader.class.getClassLoader().getResource(PROPERTIES_FILE_NAME).getPath();

        InputStream inputStream = new FileInputStream(filePath);
        properties.load(inputStream);
        inputStream.close();

        return properties;
    }
}
