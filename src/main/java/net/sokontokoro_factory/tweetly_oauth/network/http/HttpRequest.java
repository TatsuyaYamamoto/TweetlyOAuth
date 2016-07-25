package net.sokontokoro_factory.tweetly_oauth.network.http;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * Created by TATSUYA-PC4 on 2016/04/09.
 */
public class HttpRequest {

    public enum method{
        GET, POST
    }

    @Getter
    @Setter
    private method method;

    @Getter
    @Setter
    private String endpoint;

    @Getter
    @Setter
    private Map<String, String> queryParams;

    @Getter
    @Setter
    private Map<String, String> headers;
}
