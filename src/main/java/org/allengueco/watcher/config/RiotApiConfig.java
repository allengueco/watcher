package org.allengueco.watcher.config;

import org.allengueco.watcher.service.AccountApi;
import org.allengueco.watcher.service.HttpRetryAfterBackOffPolicy;
import org.allengueco.watcher.service.MatchApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.retry.support.RetryTemplateBuilder;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
@EnableRetry
public class RiotApiConfig {
    private static final Logger log = LoggerFactory.getLogger(RiotApiConfig.class);
    WatcherConfiguration watcherConfiguration;

    RiotApiConfig(final WatcherConfiguration watcherConfiguration) {
        this.watcherConfiguration = watcherConfiguration;
    }

    @Bean
    RestClient riotRestClient(ClientHttpRequestInterceptor rateLimitingInterceptor) {
        return RestClient.builder()
                .baseUrl("https://americas.api.riotgames.com/")
                .requestInterceptor(rateLimitingInterceptor)
                .defaultHeaders(headers -> headers.add("X-Riot-Token", watcherConfiguration.apiKey()))
                .build();
    }

    @Bean
    ClientHttpRequestInterceptor rateLimitingInterceptor(RetryTemplate retryTemplate) {
        return (request, body, execution) -> retryTemplate.execute(context -> execution.execute(request, body));
    }

    @Bean
    RetryTemplate retryTemplate() {
        HttpRetryAfterBackOffPolicy backOffPolicy = new HttpRetryAfterBackOffPolicy();

        return new RetryTemplateBuilder().customBackoff(backOffPolicy).build();
    }

    @Bean
    HttpServiceProxyFactory riotHttpServiceProxyFactory(RestClient riotRestClient) {
        return HttpServiceProxyFactory.builderFor(RestClientAdapter.create(riotRestClient)).build();
    }

    @Bean
    AccountApi accountApi(HttpServiceProxyFactory riotHttpServiceProxyFactory) {
        return riotHttpServiceProxyFactory.createClient(AccountApi.class);
    }

    @Bean
    MatchApi matchApi(HttpServiceProxyFactory riotHttpServiceProxyFactory) {
        return riotHttpServiceProxyFactory.createClient(MatchApi.class);
    }

}
