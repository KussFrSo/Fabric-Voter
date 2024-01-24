package com.clases.gateway.utils;
import java.nio.file.Path;
import java.nio.file.Paths;
public class Constants2 {
    private Constants2(){}

    public static final String MSP_ID_ORG2 = "Org2MSP";
    public static final String CHANNEL_NAME = "mychannel";
    public static final String CHANNEL_NAME2 = "channel1";
    public static final String CHAINCODE_VOTACION_NAME = "chaincode";

    // Path to crypto materials.
    public static final Path CRYPTO_PATH_ORG2 = Paths.get("/home/ev-k8s/Escritorio/BSM/Hyperledger1/fabric-samples/test-network/organizations/peerOrganizations/org2.example.com");
    // Path to user certificate.
    public static final Path CERT_PATH_ORG2 = CRYPTO_PATH_ORG2.resolve(Paths.get("users/User1@org2.example.com/msp/signcerts/cert.pem"));
    // Path to user private key directory.
    public static final Path KEY_DIR_PATH_ORG2 = CRYPTO_PATH_ORG2.resolve(Paths.get("users/User1@org2.example.com/msp/keystore"));
    // Path to peer tls certificate.
    public static final Path TLS_CERT_PATH_ORG2 = CRYPTO_PATH_ORG2.resolve(Paths.get("peers/peer0.org2.example.com/tls/ca.crt"));

    // Gateway peer end point.
    public static final String PEER_ENDPOINT_ORG2 = "localhost:9051";
    public static final String OVERRIDE_AUTH_ORG2 = "peer0.org2.example.com";
}
