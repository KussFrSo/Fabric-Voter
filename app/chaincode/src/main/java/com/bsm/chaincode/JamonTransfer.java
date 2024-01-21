package com.bsm.chaincode;

import com.owlike.genson.Genson;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Default;
import org.hyperledger.fabric.contract.annotation.Info;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.shim.ChaincodeException;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.hyperledger.fabric.shim.ledger.KeyValue;
import org.hyperledger.fabric.shim.ledger.QueryResultsIterator;

import java.util.ArrayList;
import java.util.List;

@Contract(
        name = "JamonTransfer",
        info = @Info(
                title = "JamonTransfer contract",
                description = "Chaincode para la transacción de jampones",
                version = "0.0.1"))
@Default
public final class JamonTransfer implements ContractInterface {

    private final Genson genson = new Genson();

    private enum JamonTransferErrors {
        JAMON_NOT_FOUND,
        JAMON_ALREADY_EXISTS
    }

    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public AssetJamon registrarJamon(final Context ctx, final String id, final String raza, final String alimentacion, final String denomOrig, final String owner, final String valor) {

        ChaincodeStub stub = ctx.getStub();

        String state = stub.getStringState(id);

        if (!state.isEmpty()) {
            String errorMessage = String.format("Jamon ya registrado", id);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, JamonTransferErrors.JAMON_ALREADY_EXISTS.toString());
        }

        List<String> intermediarios = new ArrayList<>();
        intermediarios.add(owner);

        AssetJamon jamon = new AssetJamon(id, raza, alimentacion, denomOrig, owner, valor, intermediarios);

        String newState = genson.serialize(jamon);

        stub.putStringState(id, newState);

        return jamon;
    }

    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public AssetJamon imprimirJamon(final Context ctx, final String id) {
        ChaincodeStub stub = ctx.getStub();
        String state = stub.getStringState(id);

        if (state.isEmpty() || state == null) {
            String errorMessage = String.format("Jamon no registrado", id);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, JamonTransferErrors.JAMON_NOT_FOUND.toString());
        }

        AssetJamon jamon = genson.deserialize(state, AssetJamon.class);
        return jamon;
    }

    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public void borrarJamon(final Context ctx, final String id) {
        ChaincodeStub stub = ctx.getStub();

        String state = stub.getStringState(id);

        if (state.isEmpty() || state == null) {
            String errorMessage = String.format("Jamon no registrado", id);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, JamonTransferErrors.JAMON_NOT_FOUND.toString());
        }

        stub.delState(id);
    }

    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public String transferenciaJamon(final Context ctx, final String id, final String newOwner, final String newValue) {
        ChaincodeStub stub = ctx.getStub();
        String state = stub.getStringState(id);

        if (state == null || state.isEmpty()) {
            String errorMessage = String.format("Jamon no registrado", id);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, JamonTransferErrors.JAMON_NOT_FOUND.toString());
        }

        AssetJamon asset = genson.deserialize(state, AssetJamon.class);
        List<String> listaIntermediarios = asset.getIntermediarios();
        listaIntermediarios.add(newOwner);

        AssetJamon newAsset = new AssetJamon(asset.getId(), asset.getRaza(), asset.getAlimentacion(), asset.getDenominacionOrigen(), newOwner ,newValue, asset.getIntermediarios());
        String sortedJson = genson.serialize(newAsset);
        stub.putStringState(id, sortedJson);

        return "Nuevo propietario: " + newOwner;
    }

    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public String listarJamones(final Context ctx) {
        ChaincodeStub stub = ctx.getStub();

        List<AssetJamon> queryResults = new ArrayList<AssetJamon>();
        QueryResultsIterator<KeyValue> results = stub.getStateByRange("", "");

        for (KeyValue result: results) {
            AssetJamon asset = genson.deserialize(result.getStringValue(), AssetJamon.class);
            System.out.println(asset);
            queryResults.add(asset);
        }

        final String response = genson.serialize(queryResults);

        return response;
    }





}
