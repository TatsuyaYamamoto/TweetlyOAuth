package net.sokontokoro_factory.tweetly_oauth.network;

/**
 * Created by TATSUYA-PC4 on 2016/04/09.
 */
public enum TwitterResourceEndpoint {
    REQUEST_TOKEN("https://api.twitter.com/oauth/request_token"),
    ACCESS_TOKEN("https://api.twitter.com/oauth/access_token"),
    USERS_SHOW("https://api.twitter.com/1.1/users/show.json");

    public final String endpoint;
    TwitterResourceEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }
    public String getString() {
        return this.endpoint;
    }
}
