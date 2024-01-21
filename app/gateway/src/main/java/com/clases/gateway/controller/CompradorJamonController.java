package com.clases.gateway.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.clases.gateway.dto.CompradorJamonDTO;
import com.clases.gateway.service.CompradorJamonService;
@RestController
@RequestMapping(path="/compradorJamon")
@RequiredArgsConstructor
public class CompradorJamonController {
    private final CompradorJamonService compradorJamonService;

    @PostMapping(path = "/createCompradorJamon")
    public String createCompradorJamon(@RequestBody CompradorJamonDTO compradorJamonDTO) {
        return compradorJamonService.createCompradorJamon(compradorJamonDTO.getName(), compradorJamonDTO.getDni(), compradorJamonDTO.getEmail());}

    @DeleteMapping(path = "/deleteCompradorJamon")
    public String deleteCompradorJamon(@RequestParam Long id) {return compradorJamonService.deleteCompradorJamon(id);}

    @PutMapping(path = "/updateCompradorJamon")
    public String updateCompradorJamon(@RequestParam Long id, @RequestParam String newEmail) {return compradorJamonService.updateCompradorJamon(id, newEmail);}

    @GetMapping(path = "/loadCompradorJamon")
    public String loadCompradorJamon(@RequestParam String dni) {return compradorJamonService.loadCompradorJamon(dni);}
}
