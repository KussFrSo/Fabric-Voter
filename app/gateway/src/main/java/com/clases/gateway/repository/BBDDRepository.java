package com.clases.gateway.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.clases.gateway.entity.TVotantePropuesta;
import java.util.Optional;
public interface BBDDRepository extends JpaRepository<TVotantePropuesta, Long>{
    Optional<TVotantePropuesta> findByDni(String dni);
}
