package pexper.projects.project_hub.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pexper.projects.project_hub.config.AuthProperties;
import pexper.projects.project_hub.domain.AppUser;
import pexper.projects.project_hub.domain.Role;
import pexper.projects.project_hub.repositories.AppUserRepository;

import java.util.Locale;

@Component
public class UserBootstrapData implements CommandLineRunner {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthProperties authProperties;

    public UserBootstrapData(AppUserRepository appUserRepository,
                             PasswordEncoder passwordEncoder,
                             AuthProperties authProperties) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.authProperties = authProperties;
    }

    @Override
    public void run(String... args) {
        String username = authProperties.getDefaultUser();
        if (username == null || username.isBlank()) {
            return;
        }

        appUserRepository.findByUsername(username).ifPresentOrElse(
                user -> {},
                () -> appUserRepository.save(createDefaultUser(username))
        );
    }

    private AppUser createDefaultUser(String username) {
        AppUser user = new AppUser();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(authProperties.getDefaultPassword()));
        user.setRole(parseRole(authProperties.getDefaultRole()));
        return user;
    }

    private Role parseRole(String value) {
        if (value == null) {
            return Role.USER;
        }
        try {
            return Role.valueOf(value.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException ex) {
            return Role.USER;
        }
    }
}
