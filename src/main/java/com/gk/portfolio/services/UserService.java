package com.gk.portfolio.services;

import com.gk.portfolio.entities.User;
import com.gk.portfolio.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByRole(s).orElseThrow(() -> new UsernameNotFoundException(s));
        return new SecuredUser(user);
    }

    public class SecuredUser implements UserDetails {
        final private User user;

        SecuredUser(User user) {
            this.user = user;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return resolveAuthorities(user.getRole());
        }

        @Override
        public String getPassword() {
            return user.getPassword();
        }

        @Override
        public String getUsername() {
            return user.getRole();
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

        public User getUser() {
            return this.user;
        }
    }

    public static class GrantedAuthorityImpl implements GrantedAuthority {
        private final String role;

        GrantedAuthorityImpl(String role) {
            this.role = role;
        }

        @Override
        public String getAuthority() {
            return "ROLE_" + role;
        }
    }

    private List<? extends GrantedAuthority> resolveAuthorities(String role) {
        switch (role) {
            case "me":
                return Arrays.asList(new GrantedAuthorityImpl("me"),
                        new GrantedAuthorityImpl("hr"),
                        new GrantedAuthorityImpl("technical_guy"));
            case "hr":
                return Arrays.asList(new GrantedAuthorityImpl("hr"), new GrantedAuthorityImpl("hr"));
            case "technical_guy":
                return Arrays.asList(new GrantedAuthorityImpl("hr"),
                        new GrantedAuthorityImpl("technical_guy"));
            default:
                throw new UsernameNotFoundException(role);
        }
    }

    @Scheduled(fixedDelay = 5 * 1000L)
    private void noSleepHeroku() {
        userRepository.findAll();
    }
}
