package peaksoft.app_plaza2.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.GetMapping;
import peaksoft.app_plaza2.security.jwt.JwtFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private  final JwtFilter jwtFilter;
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

//    @Bean
//    public AuthenticationManager daoAuthenticationProvider() {
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setUserDetailsService(userDetailsService());
//        provider.setPasswordEncoder(passwordEncoder());
//        return new ProviderManager(provider);
//    }
    @Bean
    public AuthenticationManager daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(provider);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
        .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> {

                    authorize.requestMatchers("/api/users/sin-up","/api/users/sign-in").permitAll()
                            .requestMatchers("/swagger-ui/**",
                                    "/swagger-resources/*",
                                    "/v3/api-docs/**").permitAll()
                            .requestMatchers("/api/users/").hasAuthority("ADMIN")
//                            .requestMatchers("/api/applications/{id}").hasAnyAuthority("ADMIN", "USER")
//                            .requestMatchers("/api/applications/save").hasAuthority("ADMIN")
//                            .requestMatchers("/api/genres/save").hasAuthority("ADMIN")
//                            .requestMatchers("/api/applications/").hasAuthority("ADMIN")
//                            .requestMatchers("/api/genres/**").hasAuthority("ADMIN")
//                            .requestMatchers("/api/users/{id}").hasAuthority("ADMIN")
//                            .requestMatchers("/api/applications/{id}").hasAuthority("ADMIN")
//                            .requestMatchers("/api/genres/{id}").hasAuthority("ADMIN")
//                            .requestMatchers("/api/users/update/{id}").hasAnyAuthority("ADMIN","USER")
//                            .requestMatchers("/api/applications/update/{id}").hasAuthority("ADMIN")
//                            .requestMatchers("/api/genres/update/{id}").hasAuthority("ADMIN")
//                            .requestMatchers("/download/{id}").hasAnyAuthority("ADMIN", "USER")
//                            .requestMatchers("/api/applications/my-applications").hasAnyAuthority("ADMIN","USER")
//                            .requestMatchers("/api/users/search").hasAnyAuthority("ADMIN","USER")
//                            .requestMatchers("/api/applications/search").hasAnyAuthority("ADMIN","USER")
//                            .requestMatchers("/api/genres/search").hasAnyAuthority("ADMIN","USER")
                            .anyRequest().authenticated();
                })
//                .httpBasic(Customizer.withDefaults());
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
     .build();

    }
}

