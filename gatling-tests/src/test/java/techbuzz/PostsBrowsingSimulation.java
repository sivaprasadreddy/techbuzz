package techbuzz;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static utils.SimulationHelper.getConfig;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import java.time.Duration;
import utils.SimulationHelper;

public class PostsBrowsingSimulation extends Simulation {

    HttpProtocolBuilder httpProtocol = SimulationHelper.getHttpProtocolBuilder();

    FeederBuilder<String> categoryFeeder = csv("data/feeders/categories.csv").random();

    ChainBuilder byCategory =
            feed(categoryFeeder)
                    .repeat(5, "n")
                    .on(
                            exec(session -> session.set("pageNo", (int) session.get("n") + 1))
                                    .exec(
                                            http("Posts By Category")
                                                    .get("/c/#{category}?page=#{pageNo}"))
                                    .pause(3));

    ChainBuilder browsePosts = exec(byCategory);

    ScenarioBuilder scnBrowsePosts =
            scenario("Browse Posts").during(Duration.ofMinutes(2), "Counter").on(browsePosts);

    // ScenarioBuilder scnBrowsePosts = scenario("Browse Posts").exec(browsePosts);

    {
        setUp(scnBrowsePosts.injectOpen(rampUsers(getConfig().getInt("users")).during(10)))
                .protocols(httpProtocol)
                .assertions(
                        global().responseTime().max().lt(800),
                        global().successfulRequests().percent().is(100.0));
    }
}
