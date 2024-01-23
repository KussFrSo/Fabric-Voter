package com.clases.gateway.controller;

import com.clases.gateway.dto.VotantePropuestaDTO;
import com.clases.gateway.service.VotantePropuestaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/votantePropuesta")
@RequiredArgsConstructor
public class VotantePropuestaController {
    private final VotantePropuestaService votantePropuestaService;

    @PostMapping(path = "/createVotantePropuesta")
    public String createVotantePropuesta(@RequestBody VotantePropuestaDTO votantePropuestaDTO) {
        return votantePropuestaService.createVotantePropuesta(votantePropuestaDTO.getName(), votantePropuestaDTO.getDni(), votantePropuestaDTO.getEmail());}

    @DeleteMapping(path = "/deleteVotantePropuesta")
    public String deleteVotantePropuesta(@RequestParam Long id) {return votantePropuestaService.deleteVotantePropuesta(id);}

    @PutMapping(path = "/updateVotantePropuesta")
    public String updateVotantePropuesta(@RequestParam Long id, @RequestParam String newEmail) {return votantePropuestaService.updateVotantePropuesta(id, newEmail);}

    @GetMapping(path = "/loadVotantePropuesta")
    public String loadVotantePropuesta(@RequestParam String dni) {return votantePropuestaService.loadVotantePropuesta(dni);}
}
