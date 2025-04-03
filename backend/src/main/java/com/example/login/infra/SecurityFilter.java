package com.example.login.infra;

import com.example.login.repositories.UserRepository;
import com.example.login.services.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        try {
            var token = this.recoveryToken(request);
            if (token != null) {
                var subject = tokenService.validateToken(token);
                if (subject != null && !subject.isEmpty()) {
                    var user = userRepository.findById(subject)
                            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

                    var authentication = new UsernamePasswordAuthenticationToken(
                            user,
                            null,
                            user.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    request.setAttribute("userId", subject);
                }
            }
        } catch (Exception e) {
            logger.error("Authentication error", e);
            // Não interrompe o fluxo - talvez você queira tratar erros de forma diferente
        }

        filterChain.doFilter(request, response);
    }

    private String recoveryToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        return authHeader.replace("Bearer ", "");
    }
}