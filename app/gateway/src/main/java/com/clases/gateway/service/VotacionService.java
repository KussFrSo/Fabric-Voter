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
import com.owlike.genson.Genson;
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

    private String prettyJson(final byte[] json) {
        return prettyJson(new String(json, StandardCharsets.UTF_8));
    }

    private String prettyJson(final String json) {
        var parsedJson = JsonParser.parseString(json);
        return gson.toJson(parsedJson);
    }

    public ResponseDTO registrarVotacion(final String id,final String nombre, final Map<String, Votante> votantes, final List<Propuesta> propuestas, final int duracion) {
        ResponseDTO response = new ResponseDTO();
        Genson genson = new Genson();

        try (var gateway = fabricGateway.createConnection().connect()) {
            var network = gateway.getNetwork(Constants.CHANNEL_NAME);

            String votantesJson = genson.serialize(votantes);
            String propuestasJson = genson.serialize(propuestas);

            // Get the smart contract from the network.
            Contract contract = network.getContract(Constants.CHAINCODE_VOTACION_NAME);
            byte[] result = contract.submitTransaction("registrarVotacion", id, nombre, votantesJson, propuestasJson, String.valueOf(duracion));

            response.setCode("0");
            response.setData(prettyJson(result));
        } catch (Exception e) {
            response.setCode("1");
            response.setData(e.getMessage());
        }

        return response;
    }

    public ResponseDTO iniciarVotacion(final String idVotacion) {
        ResponseDTO response = new ResponseDTO();

        try (var gateway = fabricGateway.createConnection().connect()) {
            var network = gateway.getNetwork(Constants.CHANNEL_NAME);

            // Get the smart contract from the network.
            Contract contract = network.getContract(Constants.CHAINCODE_VOTACION_NAME);
            byte[] result = contract.submitTransaction("iniciarVotacion", idVotacion);

            response.setCode("0");
            response.setData("Votacion iniciada " + idVotacion );
        } catch (Exception e) {
            response.setCode("1");
            response.setData(e.getMessage());
        }

        return response;
    }

    public ResponseDTO votar(final String idVotacion, final String idPropuesta, final String idVotante) {
        ResponseDTO response = new ResponseDTO();

        try (var gateway = fabricGateway.createConnection().connect()) {
            var network = gateway.getNetwork(Constants.CHANNEL_NAME);

            // Get the smart contract from the network.
            Contract contract = network.getContract(Constants.CHAINCODE_VOTACION_NAME);
            byte[] result = contract.submitTransaction("votar", idVotacion, idPropuesta, idVotante);

            response.setCode("0");
            response.setData("Votante " + idVotante + " ha votado en la votacion " + idVotacion + " a la propuesta " + idPropuesta);
        } catch (Exception e) {
            response.setCode("1");
            response.setData(e.getMessage());
        }

        return response;
    }

    public ResponseDTO finalizarVotacion(final String idVotacion) {
        ResponseDTO response = new ResponseDTO();

        try (var gateway = fabricGateway.createConnection().connect()) {
            var network = gateway.getNetwork(Constants.CHANNEL_NAME);

            // Get the smart contract from the network.
            Contract contract = network.getContract(Constants.CHAINCODE_VOTACION_NAME);
            byte[] result = contract.submitTransaction("finalizarVotacion", idVotacion);

            response.setCode("0");
            response.setData("Votacion finalizada " + idVotacion + ". Propuesta ganadora "+ prettyJson(result));
        } catch (Exception e) {
            response.setCode("1");
            response.setData(e.getMessage());
        }

        return response;
    }

    public ResponseDTO delegarVoto(final String to, final String idVotacion, final String idVotante) {
        ResponseDTO response = new ResponseDTO();

        try (var gateway = fabricGateway.createConnection().connect()) {
            var network = gateway.getNetwork(Constants.CHANNEL_NAME);

            // Get the smart contract from the network.
            Contract contract = network.getContract(Constants.CHAINCODE_VOTACION_NAME);
            byte[] result = contract.submitTransaction("delegarVoto", to, idVotacion, idVotante);

            response.setCode("0");
            response.setData("El votante " + idVotante + " ha delegado el voto a "+ to + " en la votacion " + idVotacion);
        } catch (Exception e) {
            response.setCode("1");
            response.setData(e.getMessage());
        }

        return response;
    }

    public ResponseDTO getVotacion(final String id) {
        ResponseDTO response = new ResponseDTO();

        try (var gateway = fabricGateway.createConnection().connect()) {
            var network = gateway.getNetwork(Constants.CHANNEL_NAME);

            // Get the smart contract from the network.
            Contract contract = network.getContract(Constants.CHAINCODE_VOTACION_NAME);
            byte[] result  = contract.submitTransaction("getVotacion", id);

            response.setCode("0");
            response.setData(prettyJson(result));
        } catch (Exception e) {
            response.setCode("1");
            response.setData(e.getMessage());
        }

        return response;
    }

    public ResponseDTO getVotacionesActivas() {
        ResponseDTO response = new ResponseDTO();

        try (var gateway = fabricGateway.createConnection().connect()) {
            var network = gateway.getNetwork(Constants.CHANNEL_NAME);

            // Get the smart contract from the network.
            Contract contract = network.getContract(Constants.CHAINCODE_VOTACION_NAME);
            byte[] result  = contract.submitTransaction("getVotacionesActivas");

            response.setCode("0");
            response.setData(prettyJson(result));
        } catch (Exception e) {
            response.setCode("1");
            response.setData(e.getMessage());
        }

        return response;
    }

    public ResponseDTO getPropuestasDeVotacion(final String idVotacion) {
        ResponseDTO response = new ResponseDTO();

        try (var gateway = fabricGateway.createConnection().connect()) {
            var network = gateway.getNetwork(Constants.CHANNEL_NAME);

            // Get the smart contract from the network.
            Contract contract = network.getContract(Constants.CHAINCODE_VOTACION_NAME);
            byte[] result  = contract.submitTransaction("getPropuestasDeVotacion", idVotacion);

            response.setCode("0");
            response.setData(prettyJson(result));
        } catch (Exception e) {
            response.setCode("1");
            response.setData(e.getMessage());
        }

        return response;
    }
}
