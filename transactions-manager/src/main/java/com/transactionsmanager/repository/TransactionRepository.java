package com.transactionsmanager.repository;

import com.transactionsmanager.domain.TransactionEntity;
import com.transactionsmanager.repository.adapter.AdapterTransactionRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends AdapterTransactionRepository, JpaRepository<TransactionEntity, Long> {

}
