package com.ayo.monnify_api_clone.config;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.ayo.monnify_api_clone.auth.AuthenticationException;
import com.ayo.monnify_api_clone.auth.JwtService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilterChain extends OncePerRequestFilter {

    private JwtService jwtService;
    private UserDetailsService userDetailsService;
    private HandlerExceptionResolver handlerExceptionResolver;

    // inject all dependencies 
    public JwtAuthenticationFilterChain(JwtService jwtService, UserDetailsService userDetailsService, HandlerExceptionResolver handlerExceptionResolver) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.handlerExceptionResolver = handlerExceptionResolver;

    }

    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request, 
        @NonNull HttpServletResponse response, 
        @NonNull FilterChain filterChain)
            throws ServletException, IOException {
                try {
                    final String authHeader = request.getHeader("Authorization");
                    final String jwt;
                    final String userApiKey;
                    if (authHeader == null || !authHeader.startsWith("Bearer")){
                        filterChain.doFilter(request, response);
                        return;
                    }
                    jwt = authHeader.substring(7);
                    userApiKey = jwtService.getUserApiKey(jwt);
                    if (userApiKey != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                        UserDetails userDetails = userDetailsService.loadUserByUsername(userApiKey);
                        if (jwtService.validateToken(jwt, userDetails)) {
                            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                userDetails, 
                                null, 
                                userDetails.getAuthorities()
                            );
                            authenticationToken.setDetails(
                                new WebAuthenticationDetailsSource().buildDetails(request)
                            );
                            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                        }

                    }
                    filterChain.doFilter(request, response);

                } catch (ExpiredJwtException e) { 
                    handlerExceptionResolver.resolveException(request, response, null, new AuthenticationException("Token Expired"));
                } catch (UnsupportedJwtException e) {
                    handlerExceptionResolver.resolveException(request, response, null, new AuthenticationException("Token Unsupported"));
                } catch (MalformedJwtException e) {
                    handlerExceptionResolver.resolveException(request, response, null, new AuthenticationException("Token Malformed"));
                } catch (SignatureException e) {
                    handlerExceptionResolver.resolveException(request, response, null, new AuthenticationException("Invalid Signature"));
                } catch (IllegalArgumentException e) {
                    handlerExceptionResolver.resolveException(request, response, null, new AuthenticationException("Token not found"));
                } 
        
    }


}
