package com.creditmanager.credit;

import com.creditmanager.credit.enums.CreditKind;

interface AdapterCreditEntityRepository {
    boolean existsByAccountIdAndCreditKind(Long accountId, CreditKind creditKind);

    CreditEntity save(CreditEntity creditEntity);
}
