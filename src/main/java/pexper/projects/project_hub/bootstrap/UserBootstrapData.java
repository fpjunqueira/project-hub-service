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
        createDefaultUser();
        for (String spec : authProperties.getUsers()) {
            createUserFromSpec(spec);
        }
    }

    private void createDefaultUser() {
        String username = authProperties.getDefaultUser();
        if (username == null || username.isBlank()) {
            return;
        }

        appUserRepository.findByUsername(username).ifPresentOrElse(
                user -> {},
                () -> appUserRepository.save(createUser(username, authProperties.getDefaultPassword(), authProperties.getDefaultRole()))
        );
    }

    private void createUserFromSpec(String spec) {
        if (spec == null || spec.isBlank()) {
            return;
        }
        String[] parts = spec.trim().split(":", 3);
        if (parts.length < 2) {
            return;
        }
        String username = parts[0].trim();
        String password = parts[1].trim();
        String role = parts.length >= 3 ? parts[2].trim() : "USER";
        if (username.isBlank() || password.isBlank()) {
            return;
        }

        appUserRepository.findByUsername(username).ifPresentOrElse(
                user -> {},
                () -> appUserRepository.save(createUser(username, password, role))
        );
    }

    private AppUser createUser(String username, String plainPassword, String roleStr) {
        AppUser user = new AppUser();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(plainPassword));
        user.setRole(parseRole(roleStr));
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
