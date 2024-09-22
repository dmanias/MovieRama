package com.movierama.infrastructure.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class Bucket4JConfiguration {

    @Value("${rate.limit.capacity:50}")
    private long capacity;

    @Value("${rate.limit.refill:20}")
    private long refillTokens;

    @Value("${rate.limit.refill.duration:1}")
    private long refillDuration;

    @Bean
    public Bucket bucket() {
        Refill refill = Refill.intervally(refillTokens, Duration.ofMinutes(refillDuration));
        Bandwidth limit = Bandwidth.classic(capacity, refill);
        return Bucket.builder()
                .addLimit(limit)
                .build();
    }
}