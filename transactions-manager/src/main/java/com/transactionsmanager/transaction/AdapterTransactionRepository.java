package com.transactionsmanager.transaction;

import java.util.Set;

interface AdapterTransactionRepository {
    TransactionEntity save(TransactionEntity transactionEntity);
    TransactionEntity findById(long id);
    Set<TransactionEntity> findAllByUserId(long userId);
    Set<TransactionEntity> findAllByAccountId(long accountId);
}
