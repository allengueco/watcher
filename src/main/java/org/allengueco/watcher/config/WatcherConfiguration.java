package org.allengueco.watcher.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "watcher")
public record WatcherConfiguration(String apiKey) {}
