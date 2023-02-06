package utils;

import com.typesafe.config.Config;
import io.gatling.javaapi.http.HttpDsl;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import com.typesafe.config.ConfigFactory;

public class SimulationHelper {
    static final Config config = loadConfig();

    private static Config loadConfig() {
        return ConfigFactory.load();
    }

    public static Config getConfig() {
        return config;
    }

    public static HttpProtocolBuilder getHttpProtocolBuilder() {
        return HttpDsl.http.baseUrl(config.getString("app.baseUrl"))
            .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
            .acceptLanguageHeader("en-US,en;q=0.5")
            .acceptEncodingHeader("gzip, deflate")
            .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")
            //.disableFollowRedirect()
            ;
    }
}
