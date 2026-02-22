package pexper.projects.project_hub.config;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pexper.projects.project_hub.security.JwtAuthenticationFilter;
import pexper.projects.project_hub.security.JwtAuthoritiesConverter;

@Configuration
@EnableConfigurationProperties({JwtProperties.class, AuthProperties.class, SecurityClaimProperties.class})
public class SecurityConfig {

    private final AuthProperties authProperties;
    private final ObjectProvider<JwtAuthenticationFilter> jwtAuthenticationFilterProvider;
    private final SecurityClaimProperties securityClaimProperties;

    public SecurityConfig(
            AuthProperties authProperties,
            ObjectProvider<JwtAuthenticationFilter> jwtAuthenticationFilterProvider,
            SecurityClaimProperties securityClaimProperties) {
        this.authProperties = authProperties;
        this.jwtAuthenticationFilterProvider = jwtAuthenticationFilterProvider;
        this.securityClaimProperties = securityClaimProperties;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> {})
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/auth/**",
                                "/h2-console/**",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/actuator/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));

        if ("entra".equalsIgnoreCase(authProperties.getMode())) {
            JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
            jwtConverter.setJwtGrantedAuthoritiesConverter(
                    new JwtAuthoritiesConverter(securityClaimProperties.getGroupRoleMappings()));
            http.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtConverter)));
        } else {
            JwtAuthenticationFilter filter = jwtAuthenticationFilterProvider.getIfAvailable();
            if (filter != null) {
                http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
            }
        }

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
