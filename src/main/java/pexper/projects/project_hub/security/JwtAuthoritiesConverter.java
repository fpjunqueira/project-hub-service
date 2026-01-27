package pexper.projects.project_hub.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

public class JwtAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    private final Map<String, String> groupRoleMappings;

    public JwtAuthoritiesConverter(Map<String, String> groupRoleMappings) {
        this.groupRoleMappings = groupRoleMappings == null
                ? Collections.emptyMap()
                : Map.copyOf(groupRoleMappings);
    }

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        List<String> roles = new ArrayList<>();
        addListClaim(jwt, "roles", roles);
        addGroupMappings(jwt, roles);
        addScopes(jwt, roles);

        return roles.stream()
                .distinct()
                .map(role -> role.startsWith("SCOPE_") ? role : "ROLE_" + role)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    private void addListClaim(Jwt jwt, String claimName, List<String> target) {
        Object claim = jwt.getClaims().get(claimName);
        if (!(claim instanceof List<?> list)) {
            return;
        }
        for (Object value : list) {
            if (value != null) {
                target.add(value.toString());
            }
        }
    }

    private void addGroupMappings(Jwt jwt, List<String> target) {
        Object claim = jwt.getClaims().get("groups");
        if (!(claim instanceof List<?> list)) {
            return;
        }
        for (Object value : list) {
            if (value == null) {
                continue;
            }
            String groupId = value.toString();
            String mappedRole = groupRoleMappings.get(groupId);
            if (mappedRole != null && !mappedRole.isBlank()) {
                target.add(mappedRole.trim());
            } else {
                target.add(groupId);
            }
        }
    }

    private void addScopes(Jwt jwt, List<String> target) {
        String scopeClaim = jwt.getClaimAsString("scp");
        if (scopeClaim == null || scopeClaim.isBlank()) {
            return;
        }
        for (String scope : scopeClaim.split(" ")) {
            target.add("SCOPE_" + scope);
        }
    }
}
