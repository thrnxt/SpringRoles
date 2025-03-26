package org.thr.crudrest;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
                .map(role -> (GrantedAuthority) () -> "ROLE_" + role.getName()) // добавляем ROLE_
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
