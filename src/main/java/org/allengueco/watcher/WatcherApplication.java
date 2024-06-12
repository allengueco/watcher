package org.allengueco.watcher;

import org.allengueco.watcher.config.WatcherConfiguration;
import org.allengueco.watcher.model.Summoner;
import org.allengueco.watcher.model.match.Match;
import org.allengueco.watcher.service.AccountApi;
import org.allengueco.watcher.service.MatchApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@EnableConfigurationProperties(WatcherConfiguration.class)
public class WatcherApplication {

    public static void main(String[] args) {
        SpringApplication.run(WatcherApplication.class, args);
    }

}
