package com.clases.gateway.controller;

import com.clases.gateway.dto.*;
import com.clases.gateway.service.VotacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/votacion")
@RequiredArgsConstructor
public class VotacionController {

    private final VotacionService votacionService;

    @PostMapping(path = "/registrarVotacion")
    public ResponseDTO registrarVotacion(@RequestBody RegistrarVotacionDTO dto) {
        return votacionService.registrarVotacion(dto.getId(),dto.getNombre(),dto.getVotantes(),dto.getPropuestas(),dto.getDuracion());}

    @PostMapping(path = "/iniciarVotacion")
    public ResponseDTO iniciarVotacion(@RequestBody String idVotacion) {
        return votacionService.iniciarVotacion(idVotacion);}

    @PostMapping(path = "/votar")
    public ResponseDTO votar(@RequestBody VotarDTO dto) {
        return votacionService.votar(dto.getIdVotacion(),dto.getIdPropuesta(),dto.getIdVotante());}

    @PostMapping(path = "/finalizarVotacion")
    public ResponseDTO finalizarVotacion(@RequestBody String idVotacion) {
        return votacionService.finalizarVotacion(idVotacion);}

    @PostMapping(path = "/delegarVoto")
    public ResponseDTO delegarVoto(@RequestBody DelegarVotoDTO dto) {
        return votacionService.delegarVoto(dto.getTo(),dto.getIdVotacion(),dto.getIdVotante());}

    @GetMapping(path = "/getVotacion")
    public ResponseDTO getVotacion(@RequestParam String id) {
        return votacionService.getVotacion(id);}

    @GetMapping(path = "/getVotacionesActivas")
    public ResponseDTO getVotacionesActivas() {
        return votacionService.getVotacionesActivas();}

    @GetMapping(path = "/getPropuestasDeVotacion")
    public ResponseDTO getPropuestasDeVotacion(@RequestParam String idVotacion) {
        return votacionService.getPropuestasDeVotacion(idVotacion);}

}
