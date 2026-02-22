package pexper.projects.project_hub.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "app.auth")
public class AuthProperties {

    /**
     * Authentication mode: {@code local} uses username/password and local JWT;
     * {@code entra} uses Azure Entra ID tokens via OAuth2 resource server.
     */
    private String mode = "local";

    private String defaultUser = "admin";
    private String defaultPassword = "admin123";
    private String defaultRole = "ADMIN";

    /**
     * Additional users to create at startup. Format: username:password:role (e.g. "john:secret123:USER").
     * The default user from defaultUser/defaultPassword/defaultRole is always created first.
     */
    private List<String> users = new ArrayList<>();

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users != null ? users : new ArrayList<>();
    }

    public String getDefaultUser() {
        return defaultUser;
    }

    public void setDefaultUser(String defaultUser) {
        this.defaultUser = defaultUser;
    }

    public String getDefaultPassword() {
        return defaultPassword;
    }

    public void setDefaultPassword(String defaultPassword) {
        this.defaultPassword = defaultPassword;
    }

    public String getDefaultRole() {
        return defaultRole;
    }

    public void setDefaultRole(String defaultRole) {
        this.defaultRole = defaultRole;
    }
}
