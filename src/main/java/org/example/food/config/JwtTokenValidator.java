package org.example.food.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

import static org.aspectj.weaver.tools.cache.SimpleCacheFactory.path;

public class JwtTokenValidator extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException{
        if (path.equals("/auth/signup") || path.equals("/auth/signin")) {
            filterChain.doFilter(request, response);
            return;
        }
        String jwt=request.getHeader(JwtConstant.JWT_HEADER);
        if(jwt!=null){
            jwt=jwt.substring(7);
            try{
                SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
                Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
                String email=String.valueOf(claims.get("email"));
                String authorities=String.valueOf(claims.get("authorities"));
                List<GrantedAuthority> auth= AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
                Authentication authentication=new UsernamePasswordAuthenticationToken(email,null,auth);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            catch(Exception e){
                throw new BadCredentialsException("Invalid token....");
            }
        }
        filterChain.doFilter(request, response);

    }

}
