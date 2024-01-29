package com.clases.gateway.repository;

import com.clases.gateway.utils.Constants;
import io.grpc.Grpc;
import io.grpc.ManagedChannel;
import io.grpc.TlsChannelCredentials;
import org.hyperledger.fabric.client.identity.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

@Component
public class FabricGateway {

    public org.hyperledger.fabric.client.Gateway.Builder createConnection() throws IOException, CertificateException, InvalidKeyException, InterruptedException {

        var channel = newGrpcConnection();

        var builder = org.hyperledger.fabric.client.Gateway.newInstance().identity(newIdentity()).signer(newSigner()).connection(channel)
                // Default timeouts for different gRPC calls
                .evaluateOptions(options -> options.withDeadlineAfter(5, TimeUnit.SECONDS))
                .endorseOptions(options -> options.withDeadlineAfter(15, TimeUnit.SECONDS))
                .submitOptions(options -> options.withDeadlineAfter(5, TimeUnit.SECONDS))
                .commitStatusOptions(options -> options.withDeadlineAfter(1, TimeUnit.MINUTES));

        return builder;
}


    private static ManagedChannel newGrpcConnection() throws IOException {
        var credentials = TlsChannelCredentials.newBuilder()
                .trustManager(Constants.TLS_CERT_PATH_ORG1.toFile())
                .build();
        return Grpc.newChannelBuilder(Constants.PEER_ENDPOINT_ORG1, credentials)
                .overrideAuthority(Constants.OVERRIDE_AUTH_ORG1)
                .build();
    }

    private static Identity newIdentity() throws IOException, CertificateException {
        var certReader = Files.newBufferedReader(Constants.CERT_PATH_ORG1);
        var certificate = Identities.readX509Certificate(certReader);

        return new X509Identity(Constants.MSP_ID_ORG1, certificate);
    }

    private static Signer newSigner() throws IOException, InvalidKeyException {
        var keyReader = Files.newBufferedReader(getPrivateKeyPath());
        var privateKey = Identities.readPrivateKey(keyReader);

        return Signers.newPrivateKeySigner(privateKey);
    }

    private static Path getPrivateKeyPath() throws IOException {
        try (var keyFiles = Files.list(Constants.KEY_DIR_PATH_ORG1)) {
            return keyFiles.findFirst().orElseThrow();
        }
    }
}
