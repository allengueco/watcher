package org.allengueco.watcher;

import org.allengueco.watcher.config.WatcherConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(WatcherConfiguration.class)
public class WatcherApplication {

    public static void main(String[] args) {
        SpringApplication.run(WatcherApplication.class, args);
    }

}
