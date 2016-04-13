package net.sokontokoro_factory.tweetly_oauth.network.http;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * Created by TATSUYA-PC4 on 2016/04/09.
 */
public class HttpResponse {

    @Getter
    @Setter
    private Map<String, String> headers;

    @Getter
    @Setter
    private String body;
}
