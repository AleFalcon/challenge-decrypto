package com.challenge.decrypto.application.port.out;

import com.challenge.decrypto.domain.ComitenteDomain;
import com.challenge.decrypto.domain.ComitenteEntity;

public interface ComitenteDataBase {
    void saveComitente(ComitenteDomain comitenteDomain);
    ComitenteDomain getComitenteInformation(ComitenteEntity comitenteEntity);
    int getCountComitentes();
    void deleteComitente(ComitenteDomain comitenteDomain);
}
