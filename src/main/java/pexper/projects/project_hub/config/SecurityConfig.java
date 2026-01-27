package pexper.projects.project_hub.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import pexper.projects.project_hub.security.JwtAuthoritiesConverter;

@Configuration
@EnableConfigurationProperties(SecurityClaimProperties.class)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            SecurityClaimProperties claimProperties
    ) {
        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(
                new JwtAuthoritiesConverter(claimProperties.getGroupRoleMappings())
        );

        try {
            http.csrf(AbstractHttpConfigurer::disable)
                    .cors(Customizer.withDefaults())
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                            .requestMatchers(
                                    "/h2-console/**",
                                    "/swagger-ui/**",
                                    "/v3/api-docs/**",
                                    "/actuator/**"
                            ).permitAll()
                            .anyRequest().authenticated()
                    )
                    .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
                    .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtConverter)));

            return http.build();
        } catch (Exception ex) {
            throw new IllegalStateException("Failed to configure security filter chain", ex);
        }
    }

}
