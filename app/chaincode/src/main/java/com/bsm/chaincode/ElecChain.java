package com.bsm.chaincode;

import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;
import org.hyperledger.fabric.Logger;
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
import java.util.Collections;
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
    // Logger para registrar información relevante durante la ejecución del chaincode
    final Logger logger = Logger.getLogger(ElecChain.class);

    // Genson se utiliza para la serialización y deserialización de objetos a JSON
    private final Genson genson = new Genson();

    // Enumeraciones para manejar errores y constantes específicas del chaincode
    private enum ElecChainErrors {
        VOTACION_NOT_FOUND,
        VOTACION_ALREADY_EXISTS,
        VOTACION_NOT_AVAILABLE,
        VOTACION_NOT_FINISHED,
        UNAUTHORIZED_SENDER
    }
    private enum ElecChainConstants {
        MINTER_ORG_MSPID("Org1MSP"),
        MINTER_ORG_MSPID2("Org2MSP"),
        MINTER_ORG_MSPID3("Org3MSP");
        private final String prefix;

        ElecChainConstants(final String value) {
            this.prefix = value;
        }

        public String getValue() {
            return prefix;
        }
    }

    /**
     * Registra una nueva votación en el sistema.
     * Solo los administradores autorizados pueden realizar esta acción.
     *
     * @param ctx Contexto de Hyperledger Fabric.
     * @param id Identificador único de la votación.
     * @param votantesJson Mapa de votantes elegibles para la votación.
     * @param propuestasJson Lista de propuestas a votar.
     * @param duracionStr Duración de la votación en minutos.
     * @return Votacion Objeto de votación registrado.
     */
    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public Votacion registrarVotacion(final Context ctx, final String id, final String nombre, final String votantesJson, final String propuestasJson, final String duracionStr) {

        String clientMSPID = ctx.getClientIdentity().getMSPID();
        ChaincodeStub stub = ctx.getStub();
        String state = stub.getStringState(id);

        // Deserializa los objetos JSON a sus tipos originales
        Genson genson = new Genson();
        Map<String, Votante> votantes = genson.deserialize(votantesJson, new GenericType<Map<String, Votante>>(){});
        List<Propuesta> propuestas = genson.deserialize(propuestasJson, new GenericType<List<Propuesta>>(){});

        long duracion = TimeUnit.MINUTES.toMillis(Integer.parseInt(duracionStr));

        // Comprobar es un admin
        if (!clientMSPID.equalsIgnoreCase(ElecChainConstants.MINTER_ORG_MSPID.getValue())) {
            String errorMessage = String.format("Solo los administradores pueden registrar votaciones  %s", id);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, ElecChain.ElecChainErrors.UNAUTHORIZED_SENDER.toString());
        }

        // Comprobar id votacion ya registrado
        if (!state.isEmpty()) {
            String errorMessage = String.format("Votacion  %s ya registrada", id);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, ElecChain.ElecChainErrors.VOTACION_ALREADY_EXISTS.toString());
        }

        // Validar ID de la votación
        if (id == null || id.trim().isEmpty()) {
            String errorMessage = String.format("El ID %s de la votación no puede ser nulo o vacío", id);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage);
        }

        // Validar mapa de votantes
        if (votantes == null || votantes.isEmpty()) {
            String errorMessage = String.format("La lista de votantes no puede estar vacía");
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage);
        }

        // Validar lista de propuestas
        if (propuestas == null || propuestas.isEmpty()) {
            String errorMessage = String.format("La lista de propuestas no puede estar vacía");
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage);
        }

        // Validar duración de la votación
        if (duracion <= 0) {
            String errorMessage = String.format("La duración %s de la votación debe ser un valor positivo", duracion);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage);
        }

        EstadoVotacion estado = EstadoVotacion.PREPARACION;
        Votacion votacion = new Votacion(id, nombre, votantes, propuestas, estado, 0, 0, 0, 0, duracion);

        String newState = genson.serialize(votacion);

        stub.putStringState(id, newState);

        logger.info(String.format("Se ha creado esta votacion %s ", votacion));

        return votacion;
    }

    /**
     * Inicia una votación previamente registrada.
     * Verifica que el invocador tenga los permisos necesarios y que la votación esté en estado de preparación.
     *
     * @param ctx Contexto de Hyperledger Fabric.
     * @param idVotacion Identificador de la votación a iniciar.
     */
    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public void iniciarVotacion(final Context ctx, final String idVotacion) {
        ChaincodeStub stub = ctx.getStub();
        Votacion votacion = getVotacion(ctx, idVotacion);
        String clientMSPID = ctx.getClientIdentity().getMSPID();

        // Comprobar es un admin
        if (!clientMSPID.equalsIgnoreCase(ElecChainConstants.MINTER_ORG_MSPID.getValue())) {
            String errorMessage = String.format("Solo los administradores pueden registrar votaciones  %s", idVotacion);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, ElecChain.ElecChainErrors.UNAUTHORIZED_SENDER.toString());
        }

        // Comprobar estado votacion es Preparacion
        if (votacion.getEstado() != EstadoVotacion.PREPARACION) {
            String errorMessage = String.format("La votacion  %s no esta en preparacion", idVotacion);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, ElecChain.ElecChainErrors.VOTACION_NOT_AVAILABLE.toString());
        }

        // Validar ID de la votación
        if (idVotacion == null || idVotacion.trim().isEmpty()) {
            String errorMessage = String.format("El ID %s de la votación no puede ser nulo o vacío", idVotacion);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage);
        }

        EstadoVotacion estado = EstadoVotacion.ABIERTA;
        long tiempoVotacion = stub.getTxTimestamp().getEpochSecond() * 1000;
        Votacion nuevaVotacion = new Votacion(idVotacion,votacion.getNombre(), votacion.getVotantes(), votacion.getPropuestas(), estado, 0, 0, 0, tiempoVotacion, votacion.getDuracion());

        String newState = genson.serialize(nuevaVotacion);

        stub.putStringState(idVotacion, newState);

        logger.info(String.format("Se ha iniciado esta votacion %s ", nuevaVotacion));
    }

    /**
     * Permite a un votante emitir su voto en una votación activa.
     * Verifica que el votante no haya votado previamente y que la votación esté en estado abierto.
     *
     * @param ctx Contexto de Hyperledger Fabric.
     * @param idVotacion Identificador de la votación.
     * @param idPropuesta Identificador de la propuesta que se vota.
     * @param idVotante Identificador del votante.
     */
    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public void votar(final Context ctx, final String idVotacion, final String idPropuesta, final String idVotante) {
        ChaincodeStub stub = ctx.getStub();

        Votacion votacion = getVotacion(ctx, idVotacion);

        List<Propuesta> propuestas = votacion.getPropuestas();

        // Validar que el votante exista
        Map<String, Votante> v = votacion.getVotantes();
        if (!v.containsKey(idVotante)) {
            String errorMessage = String.format("Votante no encontrado: %s", idVotante);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage);
        }

        // Validar que la propuesta exista
        Propuesta p = null;
        try {
            int idPropuestaInt = Integer.parseInt(idPropuesta) - 1;
            p = propuestas.get(idPropuestaInt);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            String errorMessage = String.format("Propuesta no válida: %s" + idPropuesta);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage);
        }

        //Comprobar si ha votado ya
        if (votacion.getVotantes().get(idVotante).isVotado()) {
            String errorMessage = String.format("Ya ha votado  %s en esta votacion", idVotante);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, ElecChain.ElecChainErrors.VOTACION_NOT_AVAILABLE.toString());
        }

        // Comprobar estado votacion es Abierta
        if (votacion.getEstado() != EstadoVotacion.ABIERTA) {
            String errorMessage = String.format("La votacion  %s no esta abierta", idVotacion);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, ElecChain.ElecChainErrors.VOTACION_NOT_AVAILABLE.toString());
        }

        Propuesta propuesta = propuestas.get(Integer.parseInt(idPropuesta) -1);
        Map<String, Votante> votantes = votacion.getVotantes();
        Votante votante = votantes.get(idVotante);

        propuestas.remove(Integer.parseInt(idPropuesta) -1);
        Propuesta nuevaPropuesta = new Propuesta(propuesta.getNombre(),propuesta.getDetallePropuesta(),propuesta.getIdPropuesta(),propuesta.getVotos() + votante.getPesoVoto());
        propuestas.add(Integer.parseInt(idPropuesta) -1,nuevaPropuesta);
        votantes.remove(idVotante);
        Votante nuevoVotante = new Votante(votante.getIdVotante(),true,votante.getPesoVoto(),votante.getDelegarVotoTo(),idPropuesta);
        votantes.put(idVotante,nuevoVotante);

        Votacion nuevaVotacion = new Votacion(idVotacion, votacion.getNombre(),votantes, propuestas, votacion.getEstado(), votacion.getTotalVotantes()+1, votacion.getVotosEfectuados()+votante.getPesoVoto(), 0, votacion.getTiempoVotacion(), votacion.getDuracion());
        String newState = genson.serialize(nuevaVotacion);
        stub.putStringState(idVotacion, newState);

        logger.info(String.format("Se ha votado correctamente a la propuesta %s de la votacion %s", idPropuesta, idVotacion));
    }

    /**
     * Finaliza una votación activa y determina la propuesta ganadora.
     * Solo puede ser ejecutado por un administrador y cuando la votación ha alcanzado su tiempo de finalización.
     *
     * @param ctx Contexto de Hyperledger Fabric.
     * @param idVotacion Identificador de la votación a finalizar.
     * @return int Identificador de la propuesta ganadora.
     */
    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public int finalizarVotacion( final Context ctx, final String idVotacion) {
        String clientMSPID = ctx.getClientIdentity().getMSPID();
        ChaincodeStub stub = ctx.getStub();

        // Comprobar es un admin
        if (!clientMSPID.equalsIgnoreCase(ElecChainConstants.MINTER_ORG_MSPID.getValue())) {
            String errorMessage = String.format("Solo los administradores pueden finalizar votaciones %s", idVotacion);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, ElecChain.ElecChainErrors.UNAUTHORIZED_SENDER.toString());
        }

        Votacion votacion = getVotacion(ctx, idVotacion);
        int propuestaGanadora = 0;

        // Comprobar estado votacion es Abierta
        if (votacion.getEstado() != EstadoVotacion.ABIERTA) {
            String errorMessage = String.format("La votacion  %s no esta abierta", idVotacion);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, ElecChain.ElecChainErrors.VOTACION_NOT_AVAILABLE.toString());
        }

        // Comprobar si ha finalizado el periodo de votacion
        // Si ha terminado se pone finalizado y guarda ganador, si no, no finaliza
        if(System.currentTimeMillis() > votacion.getTiempoVotacion() + votacion.getDuracion()){
            propuestaGanadora = getPropuestaGanadora(votacion.getPropuestas());
            Votacion nuevaVotacion = new Votacion(idVotacion,votacion.getNombre(), votacion.getVotantes(), votacion.getPropuestas(), EstadoVotacion.FINALIZADA, votacion.getTotalVotantes(), votacion.getVotosEfectuados(), propuestaGanadora, votacion.getTiempoVotacion(), votacion.getDuracion());

            String newState = genson.serialize(nuevaVotacion);

            stub.putStringState(votacion.getId(), newState);
        }else{
            String errorMessage = String.format("El tiempo de la votacion  %s no ha finalizado", idVotacion);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, ElecChain.ElecChainErrors.VOTACION_NOT_FINISHED.toString());
        }
        logger.info(String.format("Se ha finalizado esta votacion %s con este resultado como vencedor %s", idVotacion, propuestaGanadora));
        return propuestaGanadora;
    }

    /**
     * Determina la propuesta ganadora de una votación basada en la cantidad de votos.
     * Este método itera sobre todas las propuestas de la votación y encuentra aquella con el mayor número de votos.
     *
     * @param propuestas Lista de propuestas de la votación.
     * @return int El identificador de la propuesta con la mayor cantidad de votos.
     *             En caso de empate se queda con la primera opción de las que esten empatadas.
     */
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

    /**
     * Delega el voto de un votante a otro en una votación activa.
     * Verifica que el votante no haya votado o delegado su voto previamente.
     *
     * @param ctx Contexto de Hyperledger Fabric.
     * @param to Identificador del votante al que se delega el voto.
     * @param idVotacion Identificador de la votación.
     * @param idVotante Identificador del votante que delega su voto.
     */
    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public void delegarVoto(final Context ctx, final String to, final String idVotacion, final String idVotante) {
        ChaincodeStub stub = ctx.getStub();

        Votacion votacion = getVotacion(ctx, idVotacion);

        Map<String, Votante> votantes = votacion.getVotantes();

        // Comprobar estado votacion es Abierta
        if (votacion.getEstado() != EstadoVotacion.ABIERTA) {
            String errorMessage = String.format("La votacion %s no esta abierta", idVotacion);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, ElecChain.ElecChainErrors.VOTACION_NOT_AVAILABLE.toString());
        }

        // Verifica que no esté intentando delegar a sí mismo
        if (to.equals(idVotante)) {
            String errorMessage = String.format("No puede delegar en sí mismo  %s", idVotante);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage);
        }

        // Verifica que el destinatario de la delegación esté registrado y tenga derecho a voto
        Votante votanteDestino = votantes.get(to);
        if (votanteDestino == null || votanteDestino.getPesoVoto() <= 0) {
            String errorMessage = String.format("Usuario %s no censado", votantes.get(to).getIdVotante());
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage);
        }

        // Verifica que el emisor puede votar
        Votante votanteEmisor = votantes.get(idVotante);
        if (votanteEmisor == null) {
            String errorMessage = String.format("Votante emisor %s no encontrado", idVotante);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage);
        }

        // Verifica que el votante emisor no haya votado o delegado ya
        if (votanteEmisor.isVotado()) {
            String errorMessage = String.format("Ya ha votado %s", idVotante);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage);
        }

        // Comprobar si ya ha delagado el voto previamente
        if (!votanteEmisor.getDelegarVotoTo().isEmpty()) {
            String errorMessage = String.format("Ya ha delegado el voto %s", idVotante);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage);
        }

        // Actualiza la delegación y el peso del voto
        votantes.remove(idVotante);
        votantes.remove(to);
        Votante nuevoVotanteEmisor = new Votante(votanteEmisor.getIdVotante(),true,0,to,votanteEmisor.getPropuestaVotada());
        Votante nuevoVotanteDestino = new Votante(votanteDestino.getIdVotante(),votanteDestino.isVotado(),votanteDestino.getPesoVoto() + votanteEmisor.getPesoVoto(),votanteDestino.getDelegarVotoTo(),votanteDestino.getPropuestaVotada());
        votantes.put(idVotante,nuevoVotanteEmisor);
        votantes.put(to,nuevoVotanteDestino);

        Votacion nuevaVotacion = new Votacion(idVotacion,votacion.getNombre(), votantes, votacion.getPropuestas(), votacion.getEstado(), votacion.getTotalVotantes(), votacion.getVotosEfectuados(), votacion.getPropuestaGanadora(), votacion.getTiempoVotacion(), votacion.getDuracion());
        String newState = genson.serialize(nuevaVotacion);

        stub.putStringState(idVotacion, newState);

        logger.info(String.format("Se ha delegado el voto de %s a %s en esta votacion %s", idVotante,to, idVotacion));
    }

    /**
     * Recupera una votación registrada en el sistema.
     * Devuelve el objeto de votación correspondiente al identificador proporcionado.
     *
     * @param ctx Contexto de Hyperledger Fabric.
     * @param id Identificador de la votación a recuperar.
     * @return Votacion Objeto de votación correspondiente.
     */
    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public Votacion getVotacion(Context ctx, String id) {
        ChaincodeStub stub = ctx.getStub();

        String votacionState = stub.getStringState(id);
        if (votacionState == null || votacionState.isEmpty()) {
            String errorMessage = String.format("Votacion no registrada %s", id);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, ElecChain.ElecChainErrors.VOTACION_NOT_FOUND.toString());
        }
        logger.info(String.format("Imprimir votacion %s", id));
        return deserializarVotacion(votacionState);
    }

    /**
     * Deserializa una cadena JSON a un objeto Votacion.
     * Utiliza Genson para el proceso de deserialización.
     *
     * @param json Cadena JSON que representa un objeto Votacion.
     * @return Votacion Objeto deserializado.
     */
    private Votacion deserializarVotacion(String json) {
        Genson genson = new Genson();
        return genson.deserialize(json, Votacion.class);
    }

    /**
      * Obtiene la lista de votaciones activas en el sistema.
      *
      * @param ctx Contexto de Hyperledger Fabric.
      * @return List<Votacion> Lista de votaciones activas.
    */
    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public List<Votacion> getVotacionesActivas(Context ctx) {
        ChaincodeStub stub = ctx.getStub();
        List<Votacion> votacionesActivas = new ArrayList<>();

        // Obtener todas las claves del estado para identificar votaciones
        QueryResultsIterator<KeyValue> allVotes = stub.getStateByRange("", "");
        for (KeyValue keyValue : allVotes) {
            String stringValue = keyValue.getStringValue();

            // Verificar si el valor es nulo o vacío antes de deserializar
            if (stringValue != null && !stringValue.isEmpty()) {
                Votacion votacion = deserializarVotacion(stringValue);

                // Verificar si la deserialización retorna un objeto no nulo
                if (votacion != null && votacion.getEstado() == EstadoVotacion.ABIERTA) {
                    votacionesActivas.add(votacion);
                }
            }
        }

        logger.info(String.format("Imprimir votaciones activas %s", votacionesActivas));
        return votacionesActivas;
    }

    /**
     * Obtiene la lista de propuestas de una votación específica.
     *
     * @param ctx Contexto de Hyperledger Fabric.
     * @param idVotacion Identificador de la votación.
     * @return List<Propuesta> Lista de propuestas de la votación.
     */
    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public List<Propuesta> getPropuestasDeVotacion(Context ctx, String idVotacion) {
        Votacion votacion = getVotacion(ctx, idVotacion);

        // Verificar si la votación y las propuestas no son nulas
        if (votacion != null && votacion.getPropuestas() != null) {
            logger.info(String.format("Imprimir propuestas de votacion %s : %s", idVotacion, votacion.getPropuestas()));
            return votacion.getPropuestas();
        } else {
            // Manejar casos nulos, por ejemplo, devolver una lista vacía
            logger.warning(String.format("No hay propuestas de votación para %s", idVotacion));
            return Collections.emptyList();
        }
    }

}
