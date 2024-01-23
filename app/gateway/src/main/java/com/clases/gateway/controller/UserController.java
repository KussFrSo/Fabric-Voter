package com.clases.gateway.controller;

import com.clases.gateway.dto.UserDTO;
import com.clases.gateway.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(path = "/")
    public String createUser(@RequestBody UserDTO userDTO) {
        return userService.createUser(
                userDTO.getName(),
                userDTO.getDni(),
                userDTO.getEmail(),
                userDTO.getHasDonated(),
                userDTO.getPassword(),
                userDTO.getLedgerId()
        );
    }

    @DeleteMapping(path = "/{id}")
    public String deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    @PutMapping(path = "/{id}")
    public String updateUser(@PathVariable Long id, @RequestParam String newEmail) {
        return userService.updateUser(id, newEmail);
    }

    @GetMapping(path = "/{id}")
    public String getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }
}
