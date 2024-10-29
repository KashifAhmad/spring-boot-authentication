package com.apprack.auth.utils;

import com.apprack.auth.constants.HttpResponseMessages;
import com.apprack.auth.service.UserService;
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
            username = jwtTokenUtil.extractUsername(jwt);
            System.out.println("Extracted_JWT: " + jwt);
            System.out.println("Extracted Username: " + username);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                UserDetails userDetails = userService.loadUserByUsername(username);
                System.out.println("UserDetails loaded: " + userDetails.getUsername());

                if (jwtTokenUtil.validateToken(jwt, userDetails.getUsername())) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    System.out.println("User authenticated: " + userDetails.getUsername());
                }
            } catch (UsernameNotFoundException e) {
                System.err.println("Username not found: " + e.getMessage());
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not found");
                return; // Stop further processing
            }
        }
        chain.doFilter(request, response);
    }


}
