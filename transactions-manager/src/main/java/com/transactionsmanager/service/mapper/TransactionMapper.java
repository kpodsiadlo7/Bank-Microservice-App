package com.transactionsmanager.service.mapper;

import com.transactionsmanager.domain.TransactionEntity;
import com.transactionsmanager.service.data.Transaction;
import com.transactionsmanager.web.dto.TransactionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class TransactionMapper {

    public TransactionDto mapToTransactionDtoFromTransaction(final Transaction transaction) {
        return new TransactionDto(
                transaction.getId(),
                transaction.getAccountId(),
                transaction.getUserId(),
                transaction.getDescription(),
                transaction.getKindTransaction(),
                transaction.getTransactionDate(),
                transaction.getValue()
        );
    }

    public TransactionEntity mapToTransactionEntityFromTransaction(final Transaction transaction) {
        return new TransactionEntity(
                transaction.getUserId(),
                transaction.getAccountId(),
                transaction.getDescription(),
                transaction.getKindTransaction(),
                transaction.getValue()
        );
    }

    public Transaction mapToTransactionFromTransactionEntity(final TransactionEntity transactionEntity) {
        return new Transaction(
                transactionEntity.getId(),
                transactionEntity.getAccountId(),
                transactionEntity.getUserId(),
                transactionEntity.getDescription(),
                transactionEntity.getKindTransaction(),
                transactionEntity.getTransactionDate(),
                transactionEntity.getValue()
        );
    }

    public Set<TransactionDto> mapToTransactionDtoSetFromTransactionSet(final Set<Transaction> transactions){
        Set<TransactionDto> lists = new HashSet<>();
        for (Transaction t: transactions){
            lists.add(mapToTransactionDtoFromTransaction(t));
        }
        return lists;
    }

    public Set<Transaction> mapToTransactionSetFromTransactionEntitySet(final Set<TransactionEntity> transactionEntities) {
        Set<Transaction> transactions = new HashSet<>();
        for (TransactionEntity t: transactionEntities){
            transactions.add(mapToTransactionFromTransactionEntity(t));
        }
        return transactions;
    }
}
