package com.transactionsmanager.service;

import com.transactionsmanager.service.data.Transaction;
import com.transactionsmanager.web.dto.TransferDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {

    public Transaction openTransaction(final Long userId, final String kindTransaction, final TransferDto transferDto) {
        log.info("open transaction");
        Transaction transaction = new Transaction();
        if (kindTransaction.equals("transfer")) {
            transaction = moneyTransfer(userId, transferDto);
            if (transaction.getKindTransaction().equals("error")) {
                log.info("something is wrong with returning transaction");
                transaction.setDescription(transaction.getDescription());
                return transaction;
            }
            return transaction;
        }
        log.info("wrong kind of transaction");
        transaction.setKindTransaction("error");
        transaction.setDescription("Wrong kind of transaction");
        return transaction;
    }

    @Transactional
    protected Transaction moneyTransfer(final Long userId, final TransferDto transferDto) {
        Transaction transaction = new Transaction();
        transaction.setKindTransaction("error");
        transaction.setDescription("description error");
        return transaction;
    }
}
