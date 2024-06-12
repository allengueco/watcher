package org.allengueco.watcher.config;

import org.allengueco.watcher.service.AccountApi;
import org.allengueco.watcher.service.MatchApi;
import org.springframework.boot.web.client.ClientHttpRequestFactories;
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class RiotApiConfig {
    WatcherConfiguration watcherConfiguration;

    RiotApiConfig(final WatcherConfiguration watcherConfiguration) {
        this.watcherConfiguration = watcherConfiguration;
    }

    @Bean
    RestClient riotRestClient() {
        return RestClient.builder()
                .baseUrl("https://americas.api.riotgames.com/")
                .defaultHeaders(headers -> headers.add("X-Riot-Token", watcherConfiguration.apiKey()))
                .build();
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
