package net.sokontokoro_factory.tweetly_oauth.network.http;

import net.sokontokoro_factory.tweetly_oauth.TweetlyOAuthException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HttpClient {
    private static final Logger logger = Logger.getLogger(HttpClient.class.getName());

    public static HttpResponse execute(HttpRequest httpRequest)throws TweetlyOAuthException{

        logger.entering(HttpClient.class.getSimpleName(), "execute", httpRequest);

        HttpURLConnection connection = null;

        try{
            /* create http request */
            URI uri = getUri(httpRequest.getEndpoint(), httpRequest.getQueryParams());
            connection = (HttpURLConnection)uri.toURL().openConnection();

            /* set HTTP Method */
            switch(httpRequest.getMethod()){
                case GET:
                    connection.setRequestMethod("GET");
                    break;
                case POST:
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
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

                    logger.log(Level.INFO, "request to Twitter is OK.");
                    logger.log(Level.INFO, "header: " + responseHeaders);
                    logger.log(Level.INFO, "param: " + responseBody);

                    break;

                case HttpURLConnection.HTTP_UNAUTHORIZED:
                    TweetlyOAuthException e = new TweetlyOAuthException(
                            TweetlyOAuthException.EXTERNAL_ERROR_SIGNATURE,
                            "TwitterServer間の通信で認証エラーが発生しました");

                    logger.throwing(HttpClient.class.getSimpleName(), "execute", e);
                    throw e;

                default:
                    String response = getResponseBody(connection);
                    logger.log(Level.WARNING, response);
                    throw new TweetlyOAuthException(TweetlyOAuthException.TWITTER_CONNECTION_ERROR, response);
            }

            /* create http response object */
            HttpResponse httpResponse = new HttpResponse();
            httpResponse.setBody(responseBody);
            httpResponse.setHeaders(responseHeaders);

            logger.exiting(HttpClient.class.getSimpleName(), "execute", httpResponse);
            return httpResponse;

        }catch(IOException e){
            throw new TweetlyOAuthException();
        }finally {
            if(connection != null) connection.disconnect();
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
        if(queryParams != null && queryParams.size() != 0){
            uri.append("?");
            for(String key: queryParams.keySet()){
                uri.append(key);
                uri.append("=");
                uri.append(queryParams.get(key));
            }
        }
        try{
            return new URI(uri.toString());
        }catch(URISyntaxException e){
            e.printStackTrace();
            throw new TweetlyOAuthException();
        }


    }

    private static String getResponseBody(HttpURLConnection connection) throws IOException {
        InputStream inputStream = connection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        StringBuilder stringBuilder = new StringBuilder();

        String line;

        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }
        bufferedReader.close();

        return stringBuilder.toString();
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
