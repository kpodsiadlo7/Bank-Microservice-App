package com.transactionsmanager.service.mapper;

import com.transactionsmanager.domain.TransactionEntity;
import com.transactionsmanager.service.data.Transaction;
import com.transactionsmanager.web.dto.TransactionDto;
import org.springframework.stereotype.Service;

@Service
public class TransactionMapper {

    public TransactionDto mapToTransactionDtoFromTransaction(final Transaction transaction) {
        return new TransactionDto(
                transaction.getId(),
                transaction.getUserId(),
                transaction.getDescription(),
                transaction.getKindTransaction(),
                transaction.getTransactionDate()
        );
    }

    public TransactionEntity mapToTransactionEntityFromTransaction(final Transaction transaction) {
        return new TransactionEntity(
                transaction.getUserId(),
                transaction.getDescription(),
                transaction.getKindTransaction()
        );
    }
}
