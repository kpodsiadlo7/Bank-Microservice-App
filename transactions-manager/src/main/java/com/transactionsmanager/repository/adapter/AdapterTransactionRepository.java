package com.transactionsmanager.repository.adapter;

import com.transactionsmanager.domain.TransactionEntity;

public interface AdapterTransactionRepository {
    TransactionEntity save(TransactionEntity transactionEntity);
    TransactionEntity findById(long id);
}
