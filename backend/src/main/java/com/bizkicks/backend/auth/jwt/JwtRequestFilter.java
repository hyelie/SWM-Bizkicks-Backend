package com.bizkicks.backend.auth.jwt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.apache.catalina.security.SecurityUtil;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    // public Authentication getAuthentication(String token){
    //     Map<String, Object> parseInfo = jwtTokenUtil.getUserParseInfo(token);
    //     List<String> roles = (List)parseInfo.get("role");
    //     Collection<GrantedAuthority> temp = new ArrayList<>();
    //     for (String role : roles){
    //         temp.add(new SimpleGrantedAuthority(role));
    //     }
    //     UserDetails userDetails = User.builder().username(String.valueOf(parseInfo.get("username"))).authorities(temp).password("asd").build();
    //     UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
    //     new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    //     return usernamePasswordAuthenticationToken;
    // }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
                                    throws ServletException, IOException {
        String requestTokenHeader = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;

        if(requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")){
            jwtToken = requestTokenHeader.substring(7);

            try{
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            }
            catch (IllegalArgumentException e){

            }
            catch(ExpiredJwtException e){

            }
        }

        if(username == null){

        }
        else if(redisTemplate.opsForValue().get(jwtToken) != null){

        }
        // else{
        //     Authentication authentication = getAuthentication(jwtToken);
        //     SecurityContextHolder.getContext().setAuthentication(authentication);;
        //     response.setHeader("username", username);
        // }

        chain.doFilter(request, response);
    }
}
