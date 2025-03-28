package org.thr.crudrest.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.thr.crudrest.model.User;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class MyUserDetails implements UserDetails {

    private final String username;
    private final String password;
    private final Set<GrantedAuthority> authoritySet;

    public MyUserDetails(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
//pring Security автоматически добавляет префикс "ROLE_". То есть, по факту он ищет "ROLE_ADMIN", а не просто "ADMIN".
        this.authoritySet = user.getRoles().stream()
                .map(role -> (GrantedAuthority) () -> role.getName()) // УБРАТЬ "ROLE_" здесь
                .collect(Collectors.toSet());

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authoritySet;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
