package com.amreshmaurya.bookstoreapp.filters;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.amreshmaurya.bookstoreapp.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String token = null;

        String cookieToken = extractTokenFromCookie(request);
        String headerToken = extractTokenFromHeader(request);

        token = (headerToken != null) ? headerToken : cookieToken;

        // No token → public endpoint or handled by security config
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String userId = jwtUtil.extractUsername(token);

            // optional: set authentication context
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    userId,
                    null,
                    List.of());

            SecurityContextHolder.getContext().setAuthentication(auth);

        } catch (Exception e) {
            // invalid token - stop request
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private String extractTokenFromHeader(HttpServletRequest request) {
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }

        return null;
    }

    
    private String extractTokenFromCookie(HttpServletRequest request) {

        if (request.getCookies() == null)
            return null;

        return Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals("accessToken"))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
    }
}
