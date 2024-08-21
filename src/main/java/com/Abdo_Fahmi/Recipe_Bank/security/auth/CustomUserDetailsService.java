package com.Abdo_Fahmi.Recipe_Bank.security.auth;

import com.Abdo_Fahmi.Recipe_Bank.security.model.UserPrincipal;
import com.Abdo_Fahmi.Recipe_Bank.user.User;
import com.Abdo_Fahmi.Recipe_Bank.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByName(username)
                            .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username ));

        return UserPrincipal.builder()
                .id(user.getId())
                .username(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }
}
