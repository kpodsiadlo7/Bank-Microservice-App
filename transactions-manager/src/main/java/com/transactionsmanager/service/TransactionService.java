package com.transactionsmanager.service;

import com.transactionsmanager.service.data.Transaction;
import com.transactionsmanager.service.mapper.TransactionMapper;
import com.transactionsmanager.web.dto.TransferDto;
import com.transactionsmanager.web.feign.FeignServiceAccountsManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {
    private final FeignServiceAccountsManager feignServiceAccountsManager;

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
        Transaction error = new Transaction();
        error.setKindTransaction("error");
        try {
            TransferDto returningTransfer = feignServiceAccountsManager.quickTransfer(userId,transferDto);
            if (returningTransfer.getAmount().equals(new BigDecimal(-1))){
                error.setDescription(returningTransfer.getUserAccountNumber());
                return error;
            }
        } catch (Exception e){
            error.setDescription("Failed connecting with accounts manager");
            return error;
        }
        return closeTransaction();
    }

    private Transaction closeTransaction() {
        Transaction transaction = new Transaction();
        transaction.setKindTransaction("ok");
        return transaction;
    }
}
