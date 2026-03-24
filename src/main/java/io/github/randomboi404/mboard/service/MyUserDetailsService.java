package io.github.randomboi404.mboard.service;

import io.github.randomboi404.mboard.model.User;
import io.github.randomboi404.mboard.model.UserPrincipal;
import io.github.randomboi404.mboard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repo.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User with specified username cannot be found.");
        }

        return new UserPrincipal(user);
    }
    
}
