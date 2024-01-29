package com.clases.gateway.controller;

import com.clases.gateway.dto.UserDTO;
import com.clases.gateway.dto.UserUpdateDTO;
import com.clases.gateway.entity.TUser;
import com.clases.gateway.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<TUser> getUser(@PathVariable Long id) {
        TUser user = userService.getUser(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<TUser> createUser(@RequestBody UserDTO userDTO) {
        TUser user = userService.createUser(
                userDTO.getName(),
                userDTO.getDni(),
                userDTO.getEmail(),
                userDTO.getHasDonated(),
                userDTO.getPassword(),
                userDTO.getLedgerId()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(
            @PathVariable Long id,
            @RequestBody UserUpdateDTO updateDTO
    ) {
        if (updateDTO.getPassword() == null && updateDTO.getHasDonated() == null) {
            return ResponseEntity.badRequest().build();
        }

        userService.updateUser(id, updateDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}
