package net.sokontokoro_factory.tweetly_oauth;

import net.sokontokoro_factory.tweetly_oauth.dto.AccessToken;
import net.sokontokoro_factory.tweetly_oauth.dto.RequestToken;
import net.sokontokoro_factory.tweetly_oauth.network.ServiceProviderConnection;
import net.sokontokoro_factory.tweetly_oauth.network.TwitterResourceEndpoint;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;


public class TweetlyOAuth {

	/**
	 * リクエストトークンを取得する
	 *
	 * @param callback 認証後にコールバックするURL
	 * @return
	 * @throws TweetlyOAuthException
     */
	public RequestToken getRequestToken(String callback){
		return ServiceProviderConnection.requestRequestToken(callback);
	}

	/**
	 * リクエストトークンをパラメーターに含んだOAuth認証用のページのURIオブジェクトを取得する。
	 *
	 * @param requestToken getRequestToken()で取得したRequestTokenオブジェクト
	 * @return
	 * @throws TweetlyOAuthException
     */
	public URI getAuthorizePageUri(RequestToken requestToken){
		URI uri = null;
		try {
			uri = new URI("https://api.twitter.com/oauth/authorize?oauth_token=" + requestToken.getToken());
		} catch (URISyntaxException e) {
			e.printStackTrace();
			throw new TweetlyOAuthException();
		}

		return uri;
	}


	/**
	 * アクセストークンを取得する
	 * @param token
	 * @param oauthVerifier
	 * @return
	 * @throws TweetlyOAuthException
     */
	public AccessToken getAccessToken(RequestToken token, String oauthVerifier){
		return  ServiceProviderConnection.requestAccessToken(token, oauthVerifier);
	}

	/**
	 * ユーザーのプロファイル情報を取得する
	 *
	 * @param userId
	 * @param accessToken
	 * @return
	 * @throws TweetlyOAuthException
     */
	public String getUsersShow(String userId, AccessToken accessToken){

		Map queryParams = new HashMap();
		queryParams.put("user_id", userId);

		return ServiceProviderConnection.get(TwitterResourceEndpoint.USERS_SHOW.getString(), queryParams, accessToken);
	}
}
