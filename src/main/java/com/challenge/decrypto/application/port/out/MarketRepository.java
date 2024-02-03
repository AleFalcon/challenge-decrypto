package com.challenge.decrypto.application.port.out;

import com.challenge.decrypto.domain.MarketEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MarketRepository  extends CrudRepository<MarketEntity, Long> {
    Optional<MarketEntity> findByCode(String marketCode);
}
