package com.ead.vocation.shared.util;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtServices jwtServices;

    private final List<String> protectedRoutes = List.of("/admins", "/freelancers", "/job-posters");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        try {
            boolean shouldFilter = protectedRoutes.stream().anyMatch(requestURI::startsWith);
            if (!shouldFilter) {
                chain.doFilter(request, response);
                return;
            }

            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || authHeader == "") {
                HttpServletRequest httpRequest = (HttpServletRequest) request;
                Cookie[] cookies = httpRequest.getCookies();
                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if ("accessToken".equals(cookie.getName())) {
                            authHeader = "Bearer " + cookie.getValue();
                            System.out.println("COOKIE CONTENTS " + cookie.getValue());
                            break;
                        }
                    }
                }
            }

            request.setAttribute("Authorization", authHeader);
            String id = null;
            String token = null;
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
                id = jwtServices.extractSubject(token);
            }

            if (id != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                String role = jwtServices.extractRole(token);

                if (jwtServices.validateToken(token, id)) {
                    UserDetails userDetails = User.builder()
                            .username(id)
                            .password("")
                            .roles(role)
                            .build();

                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    response.sendRedirect("/");
                    return;
                }
            } else {
                response.sendRedirect("/");
                return;
            }

            chain.doFilter(request, response);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Unauthorized\", \"message\": \"" + e.getMessage() + "\"}");
        }
    }
}
