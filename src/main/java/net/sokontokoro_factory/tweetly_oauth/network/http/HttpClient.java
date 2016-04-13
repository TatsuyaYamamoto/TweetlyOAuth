package net.sokontokoro_factory.tweetly_oauth.network.http;

import net.sokontokoro_factory.tweetly_oauth.TweetlyOAuthException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by TATSUYA-PC4 on 2016/04/09.
 */
public class HttpClient {

    public static HttpResponse execute(HttpRequest httpRequest)throws TweetlyOAuthException{

        HttpURLConnection connection = null;

        try{
            /* create http request */
            URI uri = getUri(httpRequest.getEndpoint(), httpRequest.getQueryParams());
            connection = (HttpURLConnection)uri.toURL().openConnection();

            /* set URI */
            switch(httpRequest.getMethod()){
                case GET:
                    connection.setRequestMethod("GET");     // httpメソッド
                    break;
                case POST:
                    connection.setRequestMethod("POST");    // httpメソッド
                    connection.setDoOutput(true);           // 出力許可。postのみの設定
                    break;
            }

            /* set request header */
            Map<String, String> requestHeaders = httpRequest.getHeaders();
            if(requestHeaders != null | requestHeaders.size() != 0){
                for(String key: requestHeaders.keySet()){
                    connection.setRequestProperty(key, requestHeaders.get(key));
                }
            }

            /* execute */
            connection.connect();

            String responseBody;
            Map responseHeaders;
            switch(connection.getResponseCode()){
                case HttpURLConnection.HTTP_OK:
                    responseBody = getResponseBody(connection);
                    responseHeaders = getResponseHeaders(connection);
                    break;
                default:
                    throw new TweetlyOAuthException();
            }

            /* create http response object */
            HttpResponse httpResponse = new HttpResponse();
            httpResponse.setBody(responseBody);
            httpResponse.setHeaders(responseHeaders);

            return httpResponse;
        }catch(IOException e){
            throw new TweetlyOAuthException();
        }finally {
            connection.disconnect();
        }
    }

    /**
     * エンドポイントとクエリパラメータ―のオブジェクトからURIオブジェクトを返す
     * @param endpoint
     * @param queryParams
     * @return
     * @throws URISyntaxException
     */
    private static URI getUri(String endpoint, Map<String, String> queryParams) throws TweetlyOAuthException {
        StringBuffer uri = new StringBuffer();
        uri.append(endpoint);
        if(queryParams != null | queryParams.size() != 0){
            uri.append("?");
            for(String key: queryParams.keySet()){
                uri.append(key);
                uri.append("=");
                uri.append(queryParams.get(key));
            }
        }
        try{
            return new URI(endpoint.toString());
        }catch(URISyntaxException e){
            e.printStackTrace();
            throw new TweetlyOAuthException();
        }


    }

    private static String getResponseBody(HttpURLConnection connection) throws IOException {
        InputStream in = connection.getInputStream();
        byte bodyByte[] = new byte[1024];
        in.read(bodyByte);
        in.close();

        return bodyByte.toString();
    }

    private static Map getResponseHeaders(HttpURLConnection connection){
        Map headers = connection.getHeaderFields();
        Iterator headerIt = headers.keySet().iterator();
        String header = null;
        while(headerIt.hasNext()){
            String headerKey = (String)headerIt.next();
            header += headerKey + "：" + headers.get(headerKey) + "\r\n";
        }
        return headers;
    }
}
