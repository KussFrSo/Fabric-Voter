package com.clases.gateway.controller;

import com.clases.gateway.dto.*;
import com.clases.gateway.service.JamonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/jamon")
@RequiredArgsConstructor
public class JamonController {

    private final JamonService jamonService;

    @PostMapping(path = "/registrarJamon")
    public ResponseDTO registrarJamon(@RequestBody RegisrtarJamonDTO dto) {
        return jamonService.registrarJamon(dto.getJamonId(), dto.getRaza(), dto.getAlimentacion(), dto.getDenomOrig(), dto.getOwner(), dto.getValor());}

    @GetMapping(path = "/cargarJamon")
    public ResponseDTO cargarJamon(@RequestParam String jamonId) {
        return jamonService.cargarJamon(jamonId);}

    @DeleteMapping(path = "/borrarJamon")
    public ResponseDTO borrarJamon(@RequestParam String jamonId) {
        return jamonService.borrarJamon(jamonId);}

    @PutMapping(path = "/transferenciaJamon")
    public ResponseDTO transferAsset(@RequestBody TransferJamonDTO dto) {
        return jamonService.transferenciaJamon(dto.getJamonId(), dto.getNewOwner(), dto.getNewValue());}

    @GetMapping(path = "/listarJamones")
    public ResponseDTO listarJamones() {
        return jamonService.listarJamones();}
}
