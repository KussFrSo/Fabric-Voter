package com.clases.gateway.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.clases.gateway.entity.TUser;
import java.util.Optional;
public interface UserRepository extends JpaRepository<TUser, Long>{
    Optional<TUser> findByEmail(String email);
}
