package com.clases.gateway.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.clases.gateway.entity.TCompradorJamon;
import java.util.Optional;
public interface BBDDRepository extends JpaRepository<TCompradorJamon, Long>{
    Optional<TCompradorJamon> findByDni(String dni);
}
