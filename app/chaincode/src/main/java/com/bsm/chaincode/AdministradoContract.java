package com.bsm.chaincode;

import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.contract.annotation.Info;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.hyperledger.fabric.shim.ledger.CompositeKey;
import java.util.List;

@Contract(
        name = "AdministradoContract",
        info = @Info(
                title = "AdministradoContract contract",
                description = "Chaincode para la gestion de admins",
                version = "0.0.1"))
public class AdministradoContract implements ContractInterface {

    private static final String ADMIN_LIST_KEY = "ADMIN_LIST";

    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public void crearAdministrador(Context ctx, String nuevoAdminId) {
        ChaincodeStub stub = ctx.getStub();

        // Verificar si el solicitante es un administrador existente
        String clientId = stub.getCreator().getId();
        if (!esAdministrador(ctx, clientId)) {
            throw new RuntimeException("Acceso denegado: solo un administrador puede agregar otro administrador.");
        }

        // Comprobar si el nuevo administrador ya está en la lista
        if (esAdministrador(ctx, nuevoAdminId)) {
            throw new RuntimeException("El usuario ya es un administrador.");
        }

        // Agregar el nuevo administrador
        CompositeKey adminKey = stub.createCompositeKey(ADMIN_LIST_KEY, nuevoAdminId);
        stub.putStringState(adminKey.toString(), nuevoAdminId);
    }

    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public boolean esAdministrador(Context ctx, String adminId) {
        ChaincodeStub stub = ctx.getStub();
        CompositeKey adminKey = stub.createCompositeKey(ADMIN_LIST_KEY, adminId);
        String foundAdminId = stub.getStringState(adminKey.toString());

        return foundAdminId != null && !foundAdminId.isEmpty();
    }


}

