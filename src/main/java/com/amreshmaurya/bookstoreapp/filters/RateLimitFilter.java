package com.amreshmaurya.bookstoreapp.filters;

import io.github.bucket4j.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitFilter implements Filter {

    private final ConcurrentHashMap<String, Bucket> cache = new ConcurrentHashMap<>();

    private Bucket resolveBucket(String key) {
        return cache.computeIfAbsent(key, k ->
                Bucket.builder()
                        .addLimit(Bandwidth.simple(10, Duration.ofMinutes(1)))
                        .build()
        );
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();

        // Swagger bypass 
        if (uri.contains("swagger") || uri.contains("api-docs")) {
            chain.doFilter(request, response);
            return;
        }

        //  userId  key 
        String key = (req.getUserPrincipal() != null)
                ? req.getUserPrincipal().getName()   // JWT user
                : req.getRemoteAddr();              // fallback IP

        Bucket bucket = resolveBucket(key);

        if (bucket.tryConsume(1)) {
            chain.doFilter(request, response);
        } else {
            res.setStatus(429);
            res.getWriter().write("Too many requests. Try later.");
        }
    }
}
