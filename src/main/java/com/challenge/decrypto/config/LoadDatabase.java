package com.challenge.decrypto.config;

import com.challenge.decrypto.application.port.out.ComitenteRepository;
import com.challenge.decrypto.application.port.out.CountryRepository;
import com.challenge.decrypto.application.port.out.MarketRepository;
import com.challenge.decrypto.domain.ComitenteEntity;
import com.challenge.decrypto.domain.CountryEntity;
import com.challenge.decrypto.domain.MarketEntity;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(ComitenteRepository comitenteRepository, MarketRepository marketRepository
            , CountryRepository countryRepository) {
        return args -> {

            CountryEntity argentina = new CountryEntity("Argentina");
            CountryEntity uruguay = new CountryEntity("Uruguay");


            MarketEntity mae = new MarketEntity("MAE", "Mercado Argentino de Valores", argentina);
            MarketEntity rofex = new MarketEntity("ROFEX", "Mercado de Futuros", argentina);
            MarketEntity ufex = new MarketEntity("UFEX", "Uruguay Futures Exchange", uruguay);

            ComitenteEntity comitenteEntity1 = new ComitenteEntity("Comitente 1");
            ComitenteEntity comitenteEntity2 = new ComitenteEntity("Comitente 2");

            mae.addComitente(comitenteEntity1);
            mae.addComitente(comitenteEntity2);
            ufex.addComitente(comitenteEntity1);
            ufex.addComitente(comitenteEntity2);
            rofex.addComitente(comitenteEntity2);

            countryRepository.save(argentina);
            countryRepository.save(uruguay);


            marketRepository.save(mae);
            marketRepository.save(rofex);
            marketRepository.save(ufex);

            comitenteRepository.save(comitenteEntity1);
            comitenteRepository.save(comitenteEntity2);
        };
    }
}
