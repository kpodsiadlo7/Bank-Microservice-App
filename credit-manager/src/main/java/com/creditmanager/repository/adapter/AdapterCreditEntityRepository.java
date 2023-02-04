package com.creditmanager.repository.adapter;

import com.creditmanager.domain.CreditEntity;
import com.creditmanager.domain.enums.CreditKind;

public interface AdapterCreditEntityRepository {
    boolean existsByAccountIdAndCreditKind(Long accountId, CreditKind creditKind);

    CreditEntity save(CreditEntity creditEntity);
}
