package com.transactionsmanager.repository.adapter;

import com.transactionsmanager.domain.TransactionEntity;

import java.util.Set;

public interface AdapterTransactionRepository {
    TransactionEntity save(TransactionEntity transactionEntity);
    TransactionEntity findById(long id);
    Set<TransactionEntity> findAllByUserId(long userId);
    Set<TransactionEntity> findAllByAccountId(long accountId);
}
