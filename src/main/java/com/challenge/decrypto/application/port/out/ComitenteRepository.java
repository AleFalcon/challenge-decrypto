package com.challenge.decrypto.application.port.out;

import com.challenge.decrypto.domain.ComitenteEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ComitenteRepository extends CrudRepository<ComitenteEntity, Long> {
    @Query("SELECT count(*) FROM ComitenteEntity")
    int countAllComitentes();
    Optional<ComitenteEntity> findByDescription(String description);
    void deleteByDescription(String description);
}
