package com.movierama.infrastructure.filter;

import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class RateLimitFilter extends OncePerRequestFilter {

    private final Bucket bucket;

    public RateLimitFilter(Bucket bucket) {
        this.bucket = bucket;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (shouldApplyRateLimit(request)) {
            ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);
            if (probe.isConsumed()) {
                response.addHeader("X-Rate-Limit-Remaining", String.valueOf(probe.getRemainingTokens()));
                filterChain.doFilter(request, response);
            } else {
                long waitForRefill = probe.getNanosToWaitForRefill() / 1_000_000_000;
                response.addHeader("X-Rate-Limit-Retry-After-Seconds", String.valueOf(waitForRefill));
                response.sendError(HttpStatus.TOO_MANY_REQUESTS.value(),
                        "You have exhausted your API Request Quota");
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }

    private boolean shouldApplyRateLimit(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Apply rate limit for non-authenticated users
        if (authentication == null || !authentication.isAuthenticated()) {
            return true;
        }

        // Apply rate limit for specific endpoints
        if (request.getRequestURI().startsWith("/api/public/")) {
            return true;
        }

        // Don't apply rate limit for admin users
        if (authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return false;
        }

        return false; // Default: don't apply rate limit
    }
}