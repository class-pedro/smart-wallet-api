package com.example.smart_wallet.infrastructure.security;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserSecurityService {
    UserDetails findByEmail(String email);
}
