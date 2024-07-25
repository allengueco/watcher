package org.allengueco.watcher.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.retry.RetryContext;
import org.springframework.retry.backoff.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;


public class HttpRetryAfterBackOffPolicy implements BackOffPolicy {
    private static final Logger log = LoggerFactory.getLogger(HttpRetryAfterBackOffPolicy.class);
    private final Sleeper sleeper = new ThreadWaitSleeper();
    @Override public BackOffContext start(RetryContext context) {
        return new RetryAfterBackOffContext(context);
    }

    @Override
    public void backOff(BackOffContext backOffContext) throws BackOffInterruptedException {
        final RetryAfterBackOffContext context = (RetryAfterBackOffContext) backOffContext;

        Long backOffPeriod = tryGetBackOffPeriod(context.getRetryContext().getLastThrowable())
                .orElse(1_000L);

        try {
            sleeper.sleep(backOffPeriod);
        }
        catch (InterruptedException e) {
            throw new BackOffInterruptedException("Thread interrupted while sleeping", e);
        }
    }

    private Optional<Long> tryGetBackOffPeriod(Throwable throwable) {
        return throwable instanceof HttpClientErrorException.TooManyRequests
                ? tryGetRetryAfterHeaderValue((HttpClientErrorException.TooManyRequests) throwable).map(TimeUnit.SECONDS::toMillis)
                : Optional.empty();
    }

    private Optional<Long> tryGetRetryAfterHeaderValue(HttpClientErrorException.TooManyRequests tooManyRequests) {
        HttpHeaders responseHeaders = tooManyRequests.getResponseHeaders();

        if (responseHeaders != null) {
            List<String> values = responseHeaders.get(HttpHeaders.RETRY_AFTER);

            if (values != null && !values.isEmpty()) {
                try {
                    return Optional.of(Long.valueOf(values.getFirst()));
                }
                catch (NumberFormatException e) {
                    log.warn(e.getMessage(), e);
                }
            }
        }
        return Optional.empty();
    }

    private static class RetryAfterBackOffContext implements BackOffContext {
        private RetryContext retryContext;

        public RetryAfterBackOffContext(RetryContext context) {
            this.retryContext = context;
        }

        public RetryContext getRetryContext() {
            return this.retryContext;
        }

        public void setRetryContext(RetryContext context) {
            this.retryContext = context;
        }
    }
}
