package com.ayo.monnify_api_clone.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.ayo.monnify_api_clone.auth.AuthenticationException;
import com.ayo.monnify_api_clone.auth.JwtService;
import com.ayo.monnify_api_clone.exception.ServiceException;
import com.ayo.monnify_api_clone.user.UserEntity;
import com.ayo.monnify_api_clone.user.UserService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilterChainAPI extends OncePerRequestFilter {


    private JwtService jwtService;
    private UserDetailsService userDetailsService;
    private HandlerExceptionResolver handlerExceptionResolver;
    private UserService userService;

    // inject all dependencies 
    public JwtAuthFilterChainAPI(JwtService jwtService, @Qualifier("userDetailsServiceAPI") UserDetailsService userDetailsService, HandlerExceptionResolver handlerExceptionResolver, UserService userService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.handlerExceptionResolver = handlerExceptionResolver;
        this.userService = userService;

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
                    System.out.println("Hey here");
                    
                        if (authHeader == null || !authHeader.startsWith("Bearer ")){
                            System.out.println("Got here!");
                            filterChain.doFilter(request, response);
                            return;
                        }
                        jwt = authHeader.substring(7);
                        userApiKey = jwtService.getUserApiKey(jwt);
                        if (userApiKey != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                            UserDetails userDetails = userDetailsService.loadUserByUsername(userApiKey);
                            UserEntity user = userService.getUserByEmail(userDetails.getUsername());
                            if (jwtService.validateTokenAPI(jwt, user)) {
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
                    e.printStackTrace();
                    handlerExceptionResolver.resolveException(request, response, null, new AuthenticationException("Token Expired"));
                } catch (UnsupportedJwtException e) {
                    handlerExceptionResolver.resolveException(request, response, null, new AuthenticationException("Token Unsupported"));
                } catch (MalformedJwtException e) {
                    handlerExceptionResolver.resolveException(request, response, null, new AuthenticationException("Token Malformed"));
                } catch (SignatureException e) {
                    handlerExceptionResolver.resolveException(request, response, null, new AuthenticationException("Invalid Signature"));
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    handlerExceptionResolver.resolveException(request, response, null, new AuthenticationException("Token not found"));
                } catch (ServiceException e) {
                    handlerExceptionResolver.resolveException(request, response, null, new AuthenticationException(e.getMessage()));
                    e.printStackTrace();
                } catch(UsernameNotFoundException e) {
                    handlerExceptionResolver.resolveException(request, response, null, new AuthenticationException("Unauthorized"));
                    e.printStackTrace();
                }
        
    }

}
