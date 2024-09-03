package com.hype.mobile.util;

import io.restassured.config.HttpClientConfig;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.conn.ConnectTimeoutException;

import java.net.URL;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;

import static io.restassured.RestAssured.given;
import static io.restassured.config.RestAssuredConfig.config;

@Slf4j
public class RestClient {
    public static final String tokenServiceURL = "https://api.stg.hype.it/rest/public/sms/otp";
    public static final Integer CONNECTION_TIMEOUT_SECONDS = 5;
    public static final Integer CONNECTION_RETRIES = 3;

    private RestClient() {
    }

    private static class Worker implements Callable<String> {
        URL serviceURL;

        Worker(URL serviceURL) {
            this.serviceURL = serviceURL;
        }

        @Override
        public String call() throws ConnectTimeoutException {
            return fetchToken(serviceURL);
        }
    }

    private static String fetchToken(URL tokenServiceURL) throws ConnectTimeoutException {
        log.debug("Calling {} to retrieve the token...", tokenServiceURL.toString());
        HttpClientConfig httpClientConfig = HttpClientConfig.httpClientConfig()
                .setParam("http.connection.timeout", CONNECTION_TIMEOUT_SECONDS);
        Response resp = given().config(config().httpClient(httpClientConfig)).get(tokenServiceURL);
        log.debug("Returned response {} with status {}", resp.getHeaders().asList(), resp.getStatusCode());
        Map<String, String> tokenMap = resp.jsonPath().get("data.LoginOTP");
        log.debug("OTP structure: {}", tokenMap.entrySet());
        Map.Entry<String, String> entry = tokenMap.entrySet().stream().filter(e -> e.getKey().endsWith("_0")).findFirst()
                .get();
        String message = entry.getValue();
        log.debug("Entry: {}, Message: {}", entry, message);
        //String token = message.split(":")[1].trim();
        //log.debug("Token found: {}", token);
        return message;
    }

    @SneakyThrows
    public static Optional<String> getToken(String matcher) {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        URL serviceURL = new URL(tokenServiceURL);
        String result = null;
        Future<String> future;
        future = executorService.submit(new Worker(serviceURL));
        for (int i = 0; i < CONNECTION_RETRIES; i++) {
            try {
                //invoke Worker.call()
                String msg = future.get();
                //get the value after the colon (eg: "[...] HYPE utilizza il codice: 897256")
                result = msg.split(":")[1].trim();
                log.debug("found matching message: {}", msg);
                //no exceptions, exit the loop
                break;
            } catch (Exception e) {
                if (e.getCause() instanceof ConnectTimeoutException) {
                    //If it was a timeout just keep looping
                    continue;
                }
                log.debug(e.getMessage());
                //it was another kind of exception: throw it
                throw e;
            }
        }
        log.debug("Returning value: {}", result);
        return Optional.ofNullable(result);
    }
}
