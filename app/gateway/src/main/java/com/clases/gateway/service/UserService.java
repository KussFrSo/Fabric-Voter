package com.clases.gateway.service;

import com.clases.gateway.dto.UserUpdateDTO;
import com.clases.gateway.entity.TUser;
import com.clases.gateway.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public TUser createUser(String name, String dni, String email, Boolean hasDonated, String password, String ledgerId) {
        Optional<TUser> existUser = userRepository.findByEmail(email);
        if (existUser.isPresent()) {
            throw new RuntimeException("Ya existe un usuario con este correo electrónico");
        }

        TUser user = new TUser(name, dni, email, password, hasDonated, ledgerId);
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        TUser user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        userRepository.delete(user);
    }

    public void updateUser(Long id, UserUpdateDTO userUpdateDTO) {
        TUser user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String newPassword = userUpdateDTO.getPassword();
        if (newPassword != null && !newPassword.isEmpty()) {
            user.setPassword(newPassword);
        }

        Boolean hasDonated = userUpdateDTO.getHasDonated();
        if (hasDonated != null) {
            user.setHasDonated(hasDonated);
        }

        userRepository.save(user);
    }

    public TUser getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
}
