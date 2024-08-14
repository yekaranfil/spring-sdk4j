package dileksoft.sdk.config;

import dileksoft.sdk.Security.SecurityService.JwtUtil;
import dileksoft.sdk.Security.SecurityService.MyUserDetailsService;
import dileksoft.sdk.Security.filter.JwtRequestFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private MyUserDetailsService myUserDetailsService;
    @Autowired
    private JwtRequestFilter jwtRequestFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtUtil jwtUtil, MyUserDetailsService myUserDetailsService) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/authentication").permitAll()
                                .requestMatchers("/public/**").permitAll()
                                .requestMatchers("/error/**").permitAll()
                                .anyRequest().authenticated()
                )
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling
                                .authenticationEntryPoint((request, response, authException) -> {
                                    response.setContentType("application/json");
                                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                                    response.getWriter().write("{\"timestamp\": " + System.currentTimeMillis() + ", \"status\": 401, \"error\": \"Unauthorized\", \"message\": \"No Token Provided - Token Gönderilmedi \", \"path\": \"" + request.getRequestURI() + "\"}");
                                })
                                .accessDeniedHandler((request, response, accessDeniedException) -> {
                                    response.setContentType("application/json");
                                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                                    response.getWriter().write("{\"timestamp\": " + System.currentTimeMillis() + ", \"status\": 403, \"error\": \"Forbidden\", \"message\": \"Access Denied\", \"path\": \"" + request.getRequestURI() + "\"}");
                                })
                )
                .addFilterBefore(new JwtRequestFilter(myUserDetailsService, jwtUtil), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }


}