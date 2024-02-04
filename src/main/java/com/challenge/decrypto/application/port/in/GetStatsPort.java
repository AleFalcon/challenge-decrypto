package com.challenge.decrypto.application.port.in;

import com.challenge.decrypto.domain.StatsDomain;

import java.util.List;

public interface GetStatsPort {
    List<StatsDomain> getStats();
}
