package com.clases.gateway.utils;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class Constants {

    private Constants(){}

    public static final String MSP_ID_ORG1 = "Org1MSP";
    public static final String CHANNEL_NAME = "mychannel";
    public static final String CHANNEL_NAME2 = "channel1";
    public static final String CHAINCODE_VOTACION_NAME = "chaincode";

    // Path to crypto materials.
    public static final Path CRYPTO_PATH_ORG1 = Paths.get("../organizations/peerOrganizations/org1.example.com");
    // Path to user certificate.
    public static final Path CERT_PATH_ORG1 = CRYPTO_PATH_ORG1.resolve(Paths.get("users/User1@org1.example.com/msp/signcerts/cert.pem"));
    // Path to user private key directory.
    public static final Path KEY_DIR_PATH_ORG1 = CRYPTO_PATH_ORG1.resolve(Paths.get("users/User1@org1.example.com/msp/keystore"));
    // Path to peer tls certificate.
    public static final Path TLS_CERT_PATH_ORG1 = CRYPTO_PATH_ORG1.resolve(Paths.get("peers/peer0.org1.example.com/tls/ca.crt"));

    // Gateway peer end point.
    public static final String PEER_ENDPOINT_ORG1 = "localhost:7051";
    public static final String OVERRIDE_AUTH_ORG1 = "peer0.org1.example.com";
}
