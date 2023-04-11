package techbuzz;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

import static utils.SimulationHelper.getConfig;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.FeederBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import utils.SimulationHelper;

import java.time.Duration;

public class PostCreationSimulation extends Simulation {

    HttpProtocolBuilder httpProtocol = SimulationHelper.getHttpProtocolBuilder();

    FeederBuilder<String> postFeeder = csv("data/feeders/posts.csv").random();
    FeederBuilder<String> credentialsFeeder = csv("data/feeders/credentials.csv").random();

    ChainBuilder login =
            feed(credentialsFeeder)
                    .exec(
                            http("Login Form")
                                    .get("/login")
                                    .check(css("input[name=_csrf]", "value").saveAs("csrf")))
                    .pause(1)
                    .exec(
                            http("Login")
                                    .post("/login")
                                    .formParam("_csrf", "#{csrf}")
                                    .formParam("username", "#{username}")
                                    .formParam("password", "#{password}"))
                    .pause(1);

    ChainBuilder createPost =
            feed(postFeeder)
                    .exec(
                            http("New Post Form")
                                    .get("/posts/new")
                                    .check(css("input[name=_csrf]", "value").saveAs("csrf")))
                    .pause(1)
                    .exec(
                            http("Create New Post")
                                    .post("/posts")
                                    .formParam("_csrf", "#{csrf}")
                                    .formParam("url", "#{url}")
                                    .formParam("title", "#{title}")
                                    .formParam("content", "#{title}")
                                    .formParam("categoryId", "#{category}"))
                    .pause(1);

    ChainBuilder createPostFlow = exec(login).pause(2).exec(createPost);

    ScenarioBuilder scnCreatePost =
            scenario("Create Post").during(Duration.ofMinutes(2), "Counter").on(createPostFlow);

    // ScenarioBuilder scnCreatePost = scenario("Create Post").exec(createPostFlow);

    {
        setUp(scnCreatePost.injectOpen(rampUsers(getConfig().getInt("users")).during(10)))
                .protocols(httpProtocol)
                .assertions(
                        global().responseTime().max().lt(800),
                        global().successfulRequests().percent().is(100.0));
    }
}
