package com.example.smart_wallet.modules.user.application.port.out;

import com.example.smart_wallet.modules.user.domain.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository {
    UserDetails findByEmail(String email);

    Optional<User> findEntityByEmail(String email);

    User save(User user);
}
