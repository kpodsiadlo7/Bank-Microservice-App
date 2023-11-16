package com.transactionsmanager.transaction;

import com.transactionsmanager.transaction.dto.TransactionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
class TransactionFactory {

    public TransactionDto mapToTransactionDtoFromTransactionEntity(final TransactionEntity transactionEntity) {
        return TransactionDto.builder()
                .withId(transactionEntity.getId())
                .withAccountId(transactionEntity.getAccountId())
                .withUserId(transactionEntity.getUserId())
                .withDescription(transactionEntity.getDescription())
                .withKindTransaction(transactionEntity.getKindTransaction())
                .withTransactionDate(transactionEntity.getTransactionDate())
                .withValue(transactionEntity.getValue()).build();
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

    public Set<TransactionDto> mapToTransactionDtoSetFromTransactionEntitySet(final Set<TransactionEntity> transactionEntity){
        Set<TransactionDto> lists = new HashSet<>();
        for (TransactionEntity t: transactionEntity){
            lists.add(mapToTransactionDtoFromTransactionEntity(t));
        }
        return lists;
    }
}
