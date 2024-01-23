package com.clases.gateway.service;

import com.clases.gateway.dto.EstadoVotacion;
import com.clases.gateway.dto.Propuesta;
import com.clases.gateway.dto.ResponseDTO;
import com.clases.gateway.dto.Votante;
import com.clases.gateway.repository.FabricGateway;
import com.clases.gateway.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.hyperledger.fabric.client.Contract;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class VotacionService {

    public final FabricGateway fabricGateway;

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public ResponseDTO registrarJamon(final String jamonID, final String raza, final String alimentacion,
                                   final String denomOrig, final String owner, final int valor) {

        ResponseDTO response = new ResponseDTO();

        try (var gateway = fabricGateway.createConnection().connect()) {
            var network = gateway.getNetwork(Constants.CHANNEL_NAME);

            // Get the smart contract from the network.
            Contract contract = network.getContract(Constants.CHAINCODE_JAMON_NAME);
            byte[] result = contract.submitTransaction("registrarJamon", jamonID, raza, alimentacion, denomOrig, owner, String.valueOf(valor));

            response.setCode("0");
            response.setData(prettyJson(result));
        } catch (Exception e) {
            response.setCode("1");
            response.setData(e.getMessage());
        }

        return response;
    }

    public ResponseDTO cargarJamon(final String jamonID) {

        ResponseDTO response = new ResponseDTO();

        try (var gateway = fabricGateway.createConnection().connect()) {
            var network = gateway.getNetwork(Constants.CHANNEL_NAME);

            // Get the smart contract from the network.
            Contract contract = network.getContract(Constants.CHAINCODE_JAMON_NAME);
            byte[] result  = contract.submitTransaction("imprimirJamon", jamonID);

            response.setCode("0");
            response.setData(prettyJson(result));
        } catch (Exception e) {
            response.setCode("1");
            response.setData(e.getMessage());
        }

        return response;
    }

    public ResponseDTO borrarJamon(final String jamonID) {

        ResponseDTO response = new ResponseDTO();

        try (var gateway = fabricGateway.createConnection().connect()) {
            var network = gateway.getNetwork(Constants.CHANNEL_NAME);

            // Get the smart contract from the network.
            Contract contract = network.getContract(Constants.CHAINCODE_JAMON_NAME);
            contract.submitTransaction("borrarJamon", jamonID);

            response.setCode("0");
            response.setData("Jamon Borrado");
        } catch (Exception e) {
            response.setCode("1");
            response.setData(e.getMessage());
        }

        return response;
    }

    public ResponseDTO transferenciaJamon(final String jamonID, final String newOwner, final int newValue) {

        ResponseDTO response = new ResponseDTO();

        try (var gateway = fabricGateway.createConnection().connect()) {
            var network = gateway.getNetwork(Constants.CHANNEL_NAME);

            // Get the smart contract from the network.
            Contract contract = network.getContract(Constants.CHAINCODE_JAMON_NAME);
            byte[] result = contract.submitTransaction("transferenciaJamon", jamonID, newOwner, String.valueOf(newValue));

            response.setCode("0");
            response.setData("New owner " + newOwner );
        } catch (Exception e) {
            response.setCode("1");
            response.setData(e.getMessage());
        }

        return response;
    }

    public ResponseDTO listarJamones() {

        ResponseDTO response = new ResponseDTO();

        try (var gateway = fabricGateway.createConnection().connect()) {
            var network = gateway.getNetwork(Constants.CHANNEL_NAME);

            // Get the smart contract from the network.
            Contract contract = network.getContract(Constants.CHAINCODE_JAMON_NAME);
            byte[] result  = contract.submitTransaction("listarJamones");

            response.setCode("0");
            response.setData(prettyJson(result));
        } catch (Exception e) {
            response.setCode("1");
            response.setData(e.getMessage());
        }

        return response;
    }

    private String prettyJson(final byte[] json) {
        return prettyJson(new String(json, StandardCharsets.UTF_8));
    }

    private String prettyJson(final String json) {
        var parsedJson = JsonParser.parseString(json);
        return gson.toJson(parsedJson);
    }


    public ResponseDTO registrarVotacion(String id, Map<String, Votante> votantes, List<Propuesta> propuestas, EstadoVotacion estadoVotacion, int totalVotantes, int votosEfectuados, int propuestaGanadora, int tiempoVotacion, int duracion) {
        return null;
    }

    public ResponseDTO iniciarVotacion(String idVotacion) {
        return null;
    }

    public ResponseDTO votar(String idVotacion, String idPropuesta, String idVotante) {
        return null;
    }

    public ResponseDTO finalizarVotacion(String idVotacion) {
        return null;
    }

    public ResponseDTO delegarVoto(String to, String idVotacion, String idVotante) {
        return null;
    }

    public ResponseDTO getVotacion(String id) {
        return null;
    }

    public ResponseDTO getVotacionesActivas() {
        return null;
    }

    public ResponseDTO getPropuestasDeVotacion(String idVotacion) {
        return null;
    }
}
