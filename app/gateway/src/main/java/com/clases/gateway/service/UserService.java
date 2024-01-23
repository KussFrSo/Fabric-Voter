package com.clases.gateway.service;

import com.clases.gateway.entity.TUser;
import com.clases.gateway.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public String createUser(String name, String dni, String email, Boolean hasDonated, String password, String ledgerId) {
        TUser user = new TUser(name, dni, email, password, hasDonated, ledgerId);

        userRepository.save(user);

        return "Usuario creado";
    }

    public String deleteUser(Long id) {

        Optional<TUser> optionalUser = userRepository.findById(id);

        if (!optionalUser.isPresent()) {
            return "Usuario no encontrado";
        }

        TUser user = optionalUser.get();
        userRepository.delete(user);
        return "Usuario borrado";
    }

    public String updateUser(Long id, String newPassword)
    {
        Optional<TUser> optionalUser = userRepository.findById(id);

        if (!optionalUser.isPresent()) {
            return "Votante propuesta no encontrado";
        }
        TUser user = optionalUser.get();
        user.setNewPassword(newPassword);
        userRepository.save(user);
        return "Usuario actualizado";
    }

    public String getUser(Long id) {
        Optional<TUser> optionalUser = userRepository.findById(id);

        if (!optionalUser.isPresent()) {
            return "User no encontrado";
        }
        TUser user = optionalUser.get();

        return user.toString();
    }
}
