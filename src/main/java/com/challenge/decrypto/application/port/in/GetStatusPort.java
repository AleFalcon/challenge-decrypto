package com.challenge.decrypto.application.port.in;

import com.challenge.decrypto.domain.StatusDomain;

import java.util.List;

public interface GetStatusPort {
    List<StatusDomain> getStatus();
}
