package com.challenge.decrypto.application.usecase;

import com.challenge.decrypto.application.port.in.GetStatusPort;
import com.challenge.decrypto.application.port.out.ComitenteDataBase;
import com.challenge.decrypto.application.port.out.CountryDataBase;
import com.challenge.decrypto.domain.CountryDomain;
import com.challenge.decrypto.domain.MarketDomain;
import com.challenge.decrypto.domain.StatusDomain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class GetStatusUseCase implements GetStatusPort {
    private final ComitenteDataBase comitenteDataBase;
    private final CountryDataBase countryDataBase;
    public GetStatusUseCase(ComitenteDataBase comitenteDataBase, CountryDataBase countryDataBase) {
        this.comitenteDataBase = comitenteDataBase;
        this.countryDataBase = countryDataBase;
    }
    @Override
    @Cacheable(value = "status", key = "#root.method.name")
    public List<StatusDomain> getStatus() {
        log.info(">> Ingreso al caso de uso obtención de estado.");
        List<StatusDomain> statuesDomain = new ArrayList<>();
        List<CountryDomain> countriesDomain = countryDataBase.getAllInformation();
        for(CountryDomain country: countriesDomain) {
            statuesDomain.add(generateMarketWithCountryInformation(country));
        }
        log.info("<< Finalizó la ejecución del CU de obtención de estado.");
        return statuesDomain;
    }
    private StatusDomain generateMarketWithCountryInformation(CountryDomain country) {
        List<Map<String, Object>> marketsList = new ArrayList<>();
        String countryName = country.getName();
        for(MarketDomain market : country.getMarkets()) {
            marketsList.add(generateMarketInformation(market, comitenteDataBase.getCountComitentes()));
        }
        return StatusDomain.builder().country(countryName).market(marketsList).build();
    }
    private Map<String, Object> generateMarketInformation(MarketDomain market, int countComitentes) {
        Map<String, Object> marketMap = new HashMap<>();
        Map<String, Object> marketPercentageMap = new HashMap<>();
        float percentage = (float) market.getComitentes().size() / countComitentes * 100;
        String marketName = market.getCode();
        percentage = Math.round(percentage * 100.0f) / 100.0f;
        marketPercentageMap.put("percentage", String.format("%.2f", percentage).concat(" %"));
        marketMap.put(marketName, marketPercentageMap);
        return marketMap;
    }
}
