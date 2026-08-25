package com.example.smart_wallet.modules.user.infrastructure.security;

import com.example.smart_wallet.infrastructure.security.UserSecurityService;
import com.example.smart_wallet.modules.user.application.port.out.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceAdapter implements UserDetailsService, UserSecurityService {

    public final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return findByEmail(email);
    }

    @Override
    public UserDetails findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
