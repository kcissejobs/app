package com.behappy.expenseapp.service;

import com.behappy.expenseapp.security.user.User;
import com.behappy.expenseapp.security.user.UserRepository;
import com.behappy.expenseapp.service.dto.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@AllArgsConstructor
@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public UserDTO getUser(String userEmail) {
        Optional<User> userOptional = userRepository.findByEmail(userEmail);
        if(userOptional.isEmpty()) return null;

        User user = userOptional.get();
        return UserDTO.builder()
                .email(user.getEmail())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .accountId(user.getAccounts().get(0).getId())
                .role(user.getRole().name())
                .id(user.getId())
                .build();
    }


}
