package com.example.smart_wallet.service.user;

import com.example.smart_wallet.domain.entity.User;
import com.example.smart_wallet.dto.CreateUserDTO;
import com.example.smart_wallet.infrastructure.repository.UserRepository;
import com.example.smart_wallet.mapper.CreateUserMapper;
import com.example.smart_wallet.service.wallet.WalletService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final WalletService walletService;

    @Override
    public void signUpUser(CreateUserDTO userDto) {
        final User newUser = createUser(userDto);

        walletService.createWallet(newUser);
    }

    @Override
    public User createUser(CreateUserDTO userDto) {
        String encryptedPassword = new BCryptPasswordEncoder().encode(userDto.getPassword());
        userDto.setPassword(encryptedPassword);
        final User user = CreateUserMapper.toEntity(userDto);

        return this.userRepository.save(user);
    }
}
