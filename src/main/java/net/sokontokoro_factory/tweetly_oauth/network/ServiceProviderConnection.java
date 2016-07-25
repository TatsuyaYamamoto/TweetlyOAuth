package net.sokontokoro_factory.tweetly_oauth.network;

import net.sokontokoro_factory.tweetly_oauth.TweetlyOAuthException;
import net.sokontokoro_factory.tweetly_oauth.dto.AccessToken;
import net.sokontokoro_factory.tweetly_oauth.dto.RequestToken;
import net.sokontokoro_factory.tweetly_oauth.logic.Authorization;
import net.sokontokoro_factory.tweetly_oauth.network.http.HttpClient;
import net.sokontokoro_factory.tweetly_oauth.network.http.HttpRequest;
import net.sokontokoro_factory.tweetly_oauth.network.http.HttpResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

public class ServiceProviderConnection {
	private static Logger logger = Logger.getLogger(ServiceProviderConnection.class.getName());

	/**
	 * request Tokenオブジェクトを返す。
	 * request token リクエストをhttp clientに送り、取得したHttpResponseオブジェクトからrequest Tokenを取り出す
	 * @param callback
	 * @return
	 * @throws TweetlyOAuthException
     */
	public static RequestToken requestRequestToken(String callback){
		logger.entering(ServiceProviderConnection.class.getSimpleName(), "requestRequestToken", callback);

		/* setup */
		Authorization authorization = new Authorization(
				Authorization.Method.POST,
				TwitterResourceEndpoint.REQUEST_TOKEN.getString());

		authorization.setOauthCallback(callback);

		Map<String, String> requestHeaders = new HashMap<>();
		requestHeaders.put("Authorization", authorization.get());

		HttpRequest httpRequest = new HttpRequest();
		httpRequest.setMethod(HttpRequest.method.POST);
		httpRequest.setEndpoint(TwitterResourceEndpoint.REQUEST_TOKEN.getString());
		httpRequest.setHeaders(requestHeaders);

		/* execute */
		HttpResponse response = HttpClient.execute(httpRequest);

		/* response */
		RequestToken requestToken = ResponseBodyParser.toRequestToken(response.getBody());

		logger.exiting(ServiceProviderConnection.class.getSimpleName(), "requestRequestToken", requestToken);
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
	public static AccessToken requestAccessToken(
			RequestToken requestToken,
			String oauthVerifier){

		Object[] params = {requestToken, oauthVerifier};
		logger.entering(ServiceProviderConnection.class.getSimpleName(), "requestRequestToken", params);

		/* setup */
		Authorization authorization = new Authorization(
				Authorization.Method.POST,
				TwitterResourceEndpoint.ACCESS_TOKEN.getString());

		authorization.setToken(requestToken);
		authorization.setOauthVerifier(oauthVerifier);

		Map requestHeaders = new HashMap<String, String>();
		requestHeaders.put("Authorization", authorization.get());

		HttpRequest httpRequest = new HttpRequest();
		httpRequest.setMethod(HttpRequest.method.POST);
		httpRequest.setEndpoint(TwitterResourceEndpoint.ACCESS_TOKEN.getString());
		httpRequest.setHeaders(requestHeaders);

		/* execute */
		HttpResponse response = HttpClient.execute(httpRequest);

		/* response */
		AccessToken accessToken = ResponseBodyParser.toAccessToken(response.getBody());

		logger.exiting(ServiceProviderConnection.class.getSimpleName(), "requestRequestToken", accessToken);
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
	public static String get(
			String endpoint,
			Map queryParams,
			AccessToken acccessToken){

		Object[] params = {endpoint, queryParams, acccessToken};
		logger.entering(ServiceProviderConnection.class.getSimpleName(), "requestRequestToken", params);

		/* setup */
		Authorization authorization = new Authorization(Authorization.Method.GET, endpoint);
		authorization.setToken(acccessToken);
		authorization.setQueryParams(queryParams);

		Map<String, String> requestHeaders = new HashMap<>();
		requestHeaders.put("Authorization", authorization.get());

		HttpRequest httpRequest = new HttpRequest();
		httpRequest.setMethod(HttpRequest.method.GET);
		httpRequest.setEndpoint(endpoint);
		httpRequest.setQueryParams(queryParams);
		httpRequest.setHeaders(requestHeaders);

		/* execute */
		HttpResponse httpResponse = HttpClient.execute(httpRequest);
		String body = httpResponse.getBody();

		logger.exiting(ServiceProviderConnection.class.getSimpleName(), "requestRequestToken", body);
		return body;
	}
}