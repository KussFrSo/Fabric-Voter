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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Contract(
        name = "ElecChain",
        info = @Info(
                title = "ElecChain contract",
                description = "Chaincode para la votacion de propuestas",
                version = "0.0.1"))
@Default
public final class ElecChain implements ContractInterface{

    private final Genson genson = new Genson();
    private enum ElecChainErrors {
        VOTACION_NOT_FOUND,
        VOTACION_ALREADY_EXISTS,
        VOTACION_NOT_AVAILABLE
    }

    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public Votacion registrarVotacion(final Context ctx, final String id, final Map<String, Votante> votantes, final List<Propuesta> propuestas, final int duracion) {

        ChaincodeStub stub = ctx.getStub();

        String state = stub.getStringState(id);

        // Comprobar id votacion ya registrado
        if (!state.isEmpty()) {
            String errorMessage = String.format("Votacion ya registrada", id);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, ElecChain.ElecChainErrors.VOTACION_ALREADY_EXISTS.toString());
        }

        // Comprobar es un admin
        // ...

        EstadoVotacion estado = EstadoVotacion.ABIERTA;
        long tiempoVotacion = System.currentTimeMillis();
        Votacion votacion = new Votacion(id, votantes, propuestas, estado, 0, 0, 0, tiempoVotacion, TimeUnit.MINUTES.toMillis(duracion));

        String newState = genson.serialize(votacion);

        stub.putStringState(id, newState);

        return votacion;
    }

    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public void votar(final Context ctx, final String idVotacion, final String idPropuesta) {
        ChaincodeStub stub = ctx.getStub();

        Votacion votacion = getVotacion(ctx, idVotacion);

        // Obtén el ID del votante que realiza la acción
        String idVotante = new String(stub.getCreator()); //Suponiendo que así se obtiene la id (1,2,3...), si no pasar esta id por parametro

        //Comprobar si ha votado ya
        if (votacion.getVotantes().get(idVotante).isVotado()) {
            String errorMessage = String.format("Ya ha votado en esta votacion", idVotante);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, ElecChain.ElecChainErrors.VOTACION_NOT_AVAILABLE.toString());
        }

        // Comprobar estado votacion es Abierta
        if (votacion.getEstado() != EstadoVotacion.ABIERTA) {
            String errorMessage = String.format("La votacion no esta abierta", idVotacion);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, ElecChain.ElecChainErrors.VOTACION_NOT_AVAILABLE.toString());
        }

        // Comprobar si ha finalizado el periodo de votacion
        // Si ha terminado se pone finalizado y guarda ganador, si no hace la votación
        if(System.currentTimeMillis() > votacion.getTiempoVotacion() + votacion.getDuracion()){

            Votacion nuevaVotacion = new Votacion(idVotacion, votacion.getVotantes(), votacion.getPropuestas(), EstadoVotacion.FINALIZADA, votacion.getTotalVotantes(), votacion.getVotosEfectuados(), getPropuestaGanadora(votacion.getPropuestas()), votacion.getTiempoVotacion(), votacion.getDuracion());

            String newState = genson.serialize(nuevaVotacion);

            stub.putStringState(votacion.getId(), newState);
        }else{
            List<Propuesta> propuestas = votacion.getPropuestas();
            Propuesta propuesta = propuestas.get(Integer.parseInt(idPropuesta) -1);
            Map<String, Votante> votantes = votacion.getVotantes();
            Votante votante = votantes.get(idVotante);

            propuesta.setVotos(propuesta.getVotos() + votante.getPesoVoto());
            votante.setVotado(true);
            votante.setPropuestaVotada(idPropuesta);

            Votacion nuevaVotacion = new Votacion(idVotacion, votantes, propuestas, votacion.getEstado(), votacion.getTotalVotantes()+1, votacion.getVotosEfectuados()+votante.getPesoVoto(), 0, votacion.getTiempoVotacion(), votacion.getDuracion());

            String newState = genson.serialize(nuevaVotacion);

            stub.putStringState(idVotacion, newState);
        }

    }

    private int getPropuestaGanadora(List<Propuesta> propuestas) {
        int idPropuestaMasVotada = 0;
        int votosPropuestaMasVotada = 0;

        for(int i = 0; i < propuestas.size(); i++) {
            if(propuestas.get(i).getVotos() > votosPropuestaMasVotada) {
                idPropuestaMasVotada = i+1;
                votosPropuestaMasVotada = propuestas.get(i).getVotos() ;
            }
        }

        return idPropuestaMasVotada;
    }

    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public void delegarVoto(final Context ctx, final String to, final String idVotacion) {
        ChaincodeStub stub = ctx.getStub();

        Votacion votacion = getVotacion(ctx, idVotacion);

        // Obtén el ID del votante que realiza la acción
        String idVotante = new String(stub.getCreator());

        // Comprobar estado votacion es Abierta
        if (votacion.getEstado() != EstadoVotacion.ABIERTA) {
            String errorMessage = String.format("La votacion no esta abierta", idVotacion);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, ElecChain.ElecChainErrors.VOTACION_NOT_AVAILABLE.toString());
        }

        // Verifica que no esté intentando delegar a sí mismo
        if (to.equals(idVotante)) {
            throw new RuntimeException("No puede delegar en sí mismo.");
        }

        // Verifica que el destinatario de la delegación esté registrado y tenga derecho a voto
        Votante votanteDestino = votacion.getVotantes().get(to);
        if (votanteDestino == null || votanteDestino.getPesoVoto() <= 0) {
            throw new RuntimeException("Usuario no censado.");
        }

        // Obtiene el votante emisor
        Votante votanteEmisor = votacion.getVotantes().get(idVotante);
        if (votanteEmisor == null) {
            throw new RuntimeException("Votante emisor no encontrado.");
        }

        // Verifica que el votante emisor no haya votado o delegado ya
        if (votanteEmisor.isVotado()) {
            throw new RuntimeException("Ya ha votado.");
        }
        if (!votanteEmisor.getDelegarVotoTo().isEmpty()) {
            throw new RuntimeException("Ya ha delegado el voto.");
        }

        // Evita bucles de delegación
        /*while (!votanteDestino.getDelegarVotoTo().isEmpty()) {
            to = votanteDestino.getDelegarVotoTo();
            votanteDestino = votacion.getVotantes().get(to);

            if (to.equals(idVotante)) {
                throw new RuntimeException("Bucle de delegación detectado.");
            }
        }*/

        // Actualiza la delegación y el peso del voto
        votanteEmisor.setDelegarVotoTo(to);
        votanteDestino.setPesoVoto(votanteDestino.getPesoVoto() + votanteEmisor.getPesoVoto());

        // Si el votante destino ya votó, actualiza el voto de la propuesta correspondiente
        if (votanteDestino.isVotado()) {
            String propuestaId = votanteDestino.getPropuestaVotada();
            Propuesta propuesta = votacion.getPropuestas().get( Integer.parseInt(propuestaId) - 1);
            propuesta.setVotos(propuesta.getVotos() + votanteEmisor.getPesoVoto());
        }

        // AÑADIR SUBIDA A LEDGER DE LA VOTACION ACTUALZADA
        //Votacion v = new Votacion(idVotacion, votantes, propuestas, votacion.getEstado(), votacion.getTotalVotantes()+1, votacion.getVotosEfectuados()+votante.getPesoVoto(), 0, votacion.getTiempoVotacion(), votacion.getDuracion());
        //Votacion v = new Votacion(idVotacion, votacion.getVotantes(), votacion.getPropuestas(), votacion.getEstado(), votacion.getTotalVotantes(), votacion.getVotosEfectuados(), getPropuestaGanadora(votacion.getPropuestas()), votacion.getTiempoVotacion(), votacion.getDuracion());
        String newState = genson.serialize(votacion);// v

        stub.putStringState(idVotacion, newState);
    }

//////////////////////////////////////
    // dar poder votacion, derecho a voto?, finalizarVotacion(aparte de al votar que lo haga ¿Pensar si dejarlo ahí o separarlo?),  controlar requires que falten como Admin,
    // repasar el control de errores

    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public Votacion getVotacion(Context ctx, String id) {
        ChaincodeStub stub = ctx.getStub();

        String votacionState = stub.getStringState(id);
        if (votacionState == null || votacionState.isEmpty()) {
            throw new RuntimeException("La votación con ID '" + id + "' no existe.");
        }

        return deserializarVotacion(votacionState);
    }

    private Votacion deserializarVotacion(String json) {
        Genson genson = new Genson();
        return genson.deserialize(json, Votacion.class);
    }

/*
    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public void darDerechoDeVoto(Context ctx, String voterId) {
        ChaincodeStub stub = ctx.getStub();

        // Verificar si el votante ya está registrado
        if (votantes.containsKey(voterId)) {
            throw new RuntimeException("Votante ya registrado.");
        }

        Votante votante = new Votante(false, 1, "",0);
        votantes.put(voterId, votante);
    }

    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public void iniciarVotacion(Context ctx) {
        // Verificar si quien invoca es un administrador
        // ...

        estado = EstadoVotacion.Abierta;
    }

    private boolean esAdministrador(Context ctx, String userId) {
        ChaincodeStub stub = ctx.getStub();

        String adminCheckKey = "esAdministrador:" + userId;
        byte[] response = stub.invokeChaincodeWithStringArgs("AdministradoContract", List.of("esAdministrador", userId)).getPayload();

        // La respuesta del chaincode podría ser un booleano o algún otro formato que hayas elegido.
        // Asegúrate de procesar la respuesta adecuadamente.
        return Boolean.parseBoolean(new String(response));
    }

    */
}
