package com.transactionsmanager.transaction;

import org.springframework.data.jpa.repository.JpaRepository;

interface TransactionRepository extends AdapterTransactionRepository, JpaRepository<TransactionEntity, Long> {

}
