package com.apprack.auth.utils;

import com.apprack.auth.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Component("authJwtRequestFilter")
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private com.apprack.auth.utils.JwtTokenUtil jwtTokenUtil;

    @Lazy
    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        final String authorizationHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            try {
                username = jwtTokenUtil.extractUsername(jwt);
                System.out.println("Extracted JWT: " + jwt);
                System.out.println("Extracted Username: " + username);
            } catch (ExpiredJwtException e) {
                System.err.println("JWT expired: " + e.getMessage());

                // Set response status and body for expired JWT
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{ \"error\": \"Token expired. Please login again.\" }");
                response.getWriter().flush();
                return; // Stop further processing
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                System.out.println("Attempting to load user with username: " + username);
                UserDetails userDetails = userService.loadUserByUsername(username);
                System.out.println("UserDetails loaded successfully for user: " + userDetails.getUsername());

                if (jwtTokenUtil.validateToken(jwt, userDetails.getUsername())) {
                    System.out.println("Token validated successfully for user: " + userDetails.getUsername());
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    System.out.println("User authenticated and SecurityContext set for user: " + userDetails.getUsername());
                } else {
                    System.out.println("Token validation failed for user: " + userDetails.getUsername());
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                    return;
                }
            } catch (UsernameNotFoundException e) {
                System.err.println("Username not found: " + e.getMessage());
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not found");
                return;
            } catch (Exception e) {
                System.err.println("Error loading user details: " + e.getMessage());
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
                return;
            }
        }

        // Proceed to the next filter if all checks are passed
        chain.doFilter(request, response);
    }
}
