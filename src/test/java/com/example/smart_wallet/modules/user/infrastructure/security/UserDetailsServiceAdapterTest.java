package com.example.smart_wallet.modules.user.infrastructure.security;

import com.example.smart_wallet.modules.user.application.port.out.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceAdapterTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceAdapter adapter;

    @Test
    void loadUserByUsernameDelegatesToRepository() {
        UserDetails userDetails = new User("pedro@example.com", "hash", List.of());
        when(userRepository.findByEmail("pedro@example.com")).thenReturn(userDetails);

        assertThat(adapter.loadUserByUsername("pedro@example.com")).isEqualTo(userDetails);
    }

    @Test
    void findByEmailDelegatesToRepository() {
        UserDetails userDetails = new User("pedro@example.com", "hash", List.of());
        when(userRepository.findByEmail("pedro@example.com")).thenReturn(userDetails);

        assertThat(adapter.findByEmail("pedro@example.com")).isEqualTo(userDetails);
    }
}
