package org.maslov.bot.app.config;


import org.maslov.bot.app.auth.JwtTokenProvider;
import org.maslov.bot.app.auth.component.JwtAuthenticationFilter;
import org.maslov.bot.app.auth.controllers.ErrorResponseHandler;
import org.maslov.bot.app.dao.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

    public static final String SIGNIN_ENTRY_POINT = "/auth/signin";
    public static final String SIGNUP_ENTRY_POINT = "/auth/signup";

    public static final String TOKEN_REFRESH_ENTRY_POINT = "/auth/refreshToken";

    private final UserRepository userRepository;
    private final ErrorResponseHandler accessDeniedHandler;

    private final HandlerExceptionResolver handlerExceptionResolver;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserDetailsService userDetailsService;

    public WebSecurityConfiguration(UserRepository userRepository, ErrorResponseHandler accessDeniedHandler, HandlerExceptionResolver handlerExceptionResolver, JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.accessDeniedHandler = accessDeniedHandler;
        this.handlerExceptionResolver = handlerExceptionResolver;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    @Profile("prod")
    @Order(1) // Process JWT chain first for API endpoints
    public SecurityFilterChain jwtFilterChain(HttpSecurity http) throws Exception {
        http
//                .securityMatcher("/api/**") // Apply to API endpoints
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF for stateless API
                .exceptionHandling(configurer -> configurer
                        .accessDeniedHandler(accessDeniedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers("/bot", "/bot**", "/actuator", "/actuator**", "/actuator/prometheus**", "/auth/**").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, userDetailsService, handlerExceptionResolver), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    @Profile("dev")
    @Order(1)
    public SecurityFilterChain devJwtFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF for stateless API
                .exceptionHandling(configurer -> configurer
                        .accessDeniedHandler(accessDeniedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers(
                                "/**",
                                "/bot",
                                "/bot**",
                                "/actuator",
                                "/actuator**",
                                "/actuator/prometheus**",
                                "/auth/**",
                                "/v3/api-docs/**",
                                "/swagger-ui/**", "/swagger-ui.html",
                                 "/swagger-resources/**",
                                 "/webjars/**")
                        .permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, userDetailsService, handlerExceptionResolver), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

//    @Bean
//    @Order(2)
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
//                .anyRequest().authenticated());
//        http.httpBasic(withDefaults());
//        // Fix in next version
//        http.csrf(AbstractHttpConfigurer::disable);
//        return http.build();
//    }



    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}