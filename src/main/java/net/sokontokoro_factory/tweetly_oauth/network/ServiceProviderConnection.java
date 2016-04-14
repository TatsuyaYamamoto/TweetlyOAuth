package net.sokontokoro_factory.tweetly_oauth.network;

import net.sokontokoro_factory.tweetly_oauth.TweetlyOAuthException;
import net.sokontokoro_factory.tweetly_oauth.dto.AccessToken;
import net.sokontokoro_factory.tweetly_oauth.dto.RequestToken;
import net.sokontokoro_factory.tweetly_oauth.logic.Authorization;
import net.sokontokoro_factory.tweetly_oauth.network.http.HttpClient;
import net.sokontokoro_factory.tweetly_oauth.network.http.HttpRequest;
import net.sokontokoro_factory.tweetly_oauth.network.http.HttpResponse;
import net.sokontokoro_factory.tweetly_oauth.util.ResponseBodyParser;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class ServiceProviderConnection {

	/**
	 * request Tokenオブジェクトを返す。
	 * request token リクエストをhttp clientに送り、取得したHttpResponseオブジェクトからrequest Tokenを取り出す
	 * @param callback
	 * @return
	 * @throws TweetlyOAuthException
     */
	public static RequestToken requestRequestToken(String callback) throws TweetlyOAuthException {
		Authorization authorization = new Authorization(Authorization.Method.POST, TwitterResourceEndpoint.REQUEST_TOKEN.getString());
		authorization.setOauthCallback(callback);

		Map requestHeaders = new HashMap<String, String>();
		requestHeaders.put("Authorization", authorization.get());

		HttpRequest httpRequest = new HttpRequest();
		httpRequest.setMethod(HttpRequest.method.POST);
		httpRequest.setEndpoint(TwitterResourceEndpoint.REQUEST_TOKEN.getString());
		httpRequest.setHeaders(requestHeaders);

		HttpResponse response = HttpClient.execute(httpRequest);
		RequestToken requestToken = ResponseBodyParser.toRequestToken(response.getBody());
		return requestToken;
	}

	/**
	 * AccessTokenオブジェクトを返す。
	 * access token リクエストをhttp clientに送り、取得したHttpResponseオブジェクトからAccessTokenを取り出す
	 *
	 * @param requestToken
	 * @param oauthVerifier
	 * @return
	 * @throws TweetlyOAuthException
     */
	public static AccessToken requestAccessToken(RequestToken requestToken, String oauthVerifier) throws TweetlyOAuthException {
		Authorization authorization = new Authorization(Authorization.Method.POST, TwitterResourceEndpoint.ACCESS_TOKEN.getString());
		authorization.setToken(requestToken);
		authorization.setOauthVerifier(oauthVerifier);

		Map requestHeaders = new HashMap<String, String>();
		requestHeaders.put("Authorization", authorization.get());

		HttpRequest httpRequest = new HttpRequest();
		httpRequest.setMethod(HttpRequest.method.POST);
		httpRequest.setEndpoint(TwitterResourceEndpoint.ACCESS_TOKEN.getString());
		httpRequest.setHeaders(requestHeaders);

		HttpResponse response = HttpClient.execute(httpRequest);
		AccessToken accessToken = ResponseBodyParser.toAccessToken(response.getBody());
		return accessToken;
	}

	/**
	 * twitter restful api のリソースに対してgetを実行する
	 * @param endpoint
	 * @param queryParams
	 * @param acccessToken
	 * @return
	 * @throws TweetlyOAuthException
     */
	public static String get(String endpoint, Map queryParams, AccessToken acccessToken) throws TweetlyOAuthException{
		Authorization authorization = new Authorization(Authorization.Method.GET, endpoint);
		authorization.setToken(acccessToken);
		authorization.setQueryParams(queryParams);

		Map requestHeaders = new HashMap<String, String>();
		requestHeaders.put("Authorization", authorization.get());

		// 実行
		HttpRequest httpRequest = new HttpRequest();
		httpRequest.setMethod(HttpRequest.method.GET);
		httpRequest.setEndpoint(endpoint);
		httpRequest.setQueryParams(queryParams);
		httpRequest.setHeaders(requestHeaders);

		HttpResponse httpResponse = HttpClient.execute(httpRequest);

		return httpResponse.getBody();
	}

	/**
	 * twitter restful api のリソースに対してpostを実行する
	 * @param endpoint
	 * @param queryParams
	 * @param acccessToken
	 * @return
	 * @throws TweetlyOAuthException
	 */
	public static String post(String endpoint, Map queryParams, AccessToken acccessToken) throws TweetlyOAuthException{
		Authorization authorization = new Authorization(Authorization.Method.POST, endpoint);
		authorization.setToken(acccessToken);
		authorization.setQueryParams(queryParams);

		Map requestHeaders = new HashMap<String, String>();
		requestHeaders.put("Authorization", authorization.get());

		// 実行
		HttpRequest httpRequest = new HttpRequest();
		httpRequest.setMethod(HttpRequest.method.GET);
		httpRequest.setEndpoint(endpoint);
		httpRequest.setQueryParams(queryParams);
		httpRequest.setHeaders(requestHeaders);

		HttpResponse httpResponse = HttpClient.execute(httpRequest);

		return httpResponse.getBody();
	}


	/**
	 * twitter apiアクセス用の request headerを作成する
	 * @param element
	 * @return
     */
	private static String getRequestHeaderAuthorization(TreeMap<String,String> element){
		StringBuffer requestHeader = new StringBuffer();
		int i = 0;
		for(String key: element.keySet()){
			if(i==0){
				requestHeader.append("OAuth ");
			}else{
				requestHeader.append(",");
			}
			requestHeader.append(key);
			requestHeader.append("=");
			requestHeader.append(element.get(key));
			i++;
		}

		return requestHeader.toString();
	}
}
