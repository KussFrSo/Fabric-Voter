package com.clases.gateway.service;

import com.clases.gateway.entity.TVotantePropuesta;
import com.clases.gateway.repository.BBDDRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VotantePropuestaService {
    private final BBDDRepository bbddRepository;

    public String createVotantePropuesta(String name, String dni, String email) {

        TVotantePropuesta votantePropuesta = new TVotantePropuesta(name, dni, email);

        bbddRepository.save(votantePropuesta);

        return "Votante propuesta creado";
    }

    public String deleteVotantePropuesta(Long id) {

        Optional<TVotantePropuesta> votantePropuestaOptional = bbddRepository.findById(id);

        if (!votantePropuestaOptional.isPresent()) {
            return "Votante propuesta no encontrado";
        }

        TVotantePropuesta votantePropuesta = votantePropuestaOptional.get();
        bbddRepository.delete(votantePropuesta);
        return "Votante propuesta borrado";
    }

    public String updateVotantePropuesta(Long id, String newEmail)
    {
        Optional<TVotantePropuesta> votantePropuestaOptional = bbddRepository.findById(id);

        if (!votantePropuestaOptional.isPresent()) {
            return "Votante propuesta no encontrado";
        }
        TVotantePropuesta votantePropuesta = votantePropuestaOptional.get();
        votantePropuesta.setEmail(newEmail);
        bbddRepository.save(votantePropuesta);
        return "Votante propuesta actualizado";
    }

    public String loadVotantePropuesta(String dni) {
        Optional<TVotantePropuesta> votantePropuestaOptional = bbddRepository.findByDni(dni);

        if (!votantePropuestaOptional.isPresent()) {
            return "Votante propuesta no encontrado";
        }
        TVotantePropuesta votantePropuesta = votantePropuestaOptional.get();

        return votantePropuesta.toString();
    }
}
