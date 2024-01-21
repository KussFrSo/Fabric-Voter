package com.clases.gateway.service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.clases.gateway.entity.TCompradorJamon;
import com.clases.gateway.repository.BBDDRepository;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompradorJamonService {
    private final BBDDRepository bbddRepository;

    public String createCompradorJamon(String name, String dni, String email) {

        TCompradorJamon compradorJamon = new TCompradorJamon(name, dni, email);

        bbddRepository.save(compradorJamon);

        return "Comprador Jamon creado";
    }

    public String deleteCompradorJamon(Long id) {

        Optional<TCompradorJamon> compradorJamonOptional = bbddRepository.findById(id);

        if (!compradorJamonOptional.isPresent()) {
            return "Comprador Jamon no encontrado";
        }

        TCompradorJamon compradorJamon = compradorJamonOptional.get();
        bbddRepository.delete(compradorJamon);
        return "Comprador Jamon borrado";
    }

    public String updateCompradorJamon(Long id, String newEmail)
    {
        Optional<TCompradorJamon> compradorJamonOptional = bbddRepository.findById(id);

        if (!compradorJamonOptional.isPresent()) {
            return "Comprador Jamon no encontrado";
        }
        TCompradorJamon compradorJamon = compradorJamonOptional.get();
        compradorJamon.setEmail(newEmail);
        bbddRepository.save(compradorJamon);
        return "Comprador Jamon actualizado";
    }

    public String loadCompradorJamon(String dni) {
        Optional<TCompradorJamon> compradorJamonOptional = bbddRepository.findByDni(dni);

        if (!compradorJamonOptional.isPresent()) {
            return "Comprador Jamon no encontrado";
        }
        TCompradorJamon compradorJamon = compradorJamonOptional.get();

        return compradorJamon.toString();
    }
}
