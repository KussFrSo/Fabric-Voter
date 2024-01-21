package com.clases.gateway.controller;


import com.clases.gateway.dto.AssetDTO;
import com.clases.gateway.dto.ResponseDTO;
import com.clases.gateway.dto.TransferDTO;
import com.clases.gateway.service.BasicService;
import com.clases.gateway.utils.Constants;
import io.grpc.Grpc;
import io.grpc.ManagedChannel;
import io.grpc.TlsChannelCredentials;
import lombok.RequiredArgsConstructor;
import org.hyperledger.fabric.client.Contract;
import org.hyperledger.fabric.client.identity.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(path="/basic")
@RequiredArgsConstructor
public class BasicController {
    private final BasicService basicService;

    @PostMapping(path = "/createAsset")
    public ResponseDTO createAsset(@RequestBody AssetDTO assetDTO) {
        return basicService.createAsset(assetDTO.getAssetID(), assetDTO.getColor(), assetDTO.getSize(), assetDTO.getOwner(), assetDTO.getAppraisedValue());}

    @GetMapping(path = "/readAsset")
    public ResponseDTO readAsset(@RequestParam String assetId) {
        return basicService.readAsset(assetId);}

    @PutMapping(path = "/updateAsset")
    public ResponseDTO updateAsset(@RequestParam String assetId, @RequestParam String color, @RequestParam int size, @RequestParam int appraisedValue) {
        return basicService.updateAsset(assetId, color, size, appraisedValue);}

    @DeleteMapping(path = "/deleteAsset")
    public ResponseDTO deleteAsset(@RequestParam String assetId) {
        return basicService.deleteAsset(assetId);}

    @GetMapping(path = "/assetExists")
    public ResponseDTO assetExists(@RequestParam String assetId) {
        return basicService.assetExists(assetId);}

    @PutMapping(path = "/transferAsset")
    public ResponseDTO transferAsset(@RequestBody TransferDTO dto) {
        return basicService.transferAsset(dto.getAssetID(), dto.getNewOwner());}

    @GetMapping(path = "/getAllAssets")
    public ResponseDTO getAllAssets() {
        return basicService.getAllAssets();}
}
