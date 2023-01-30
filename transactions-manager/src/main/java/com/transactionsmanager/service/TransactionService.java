package com.transactionsmanager.service;

import com.transactionsmanager.repository.adapter.AdapterTransactionRepository;
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
    private final TransactionMapper transactionMapper;
    private final AdapterTransactionRepository adapterTransactionRepository;

    public Transaction openTransaction(final Long userDecreaseId, final String kindTransaction, final TransferDto transferDto) {
        log.info("should be id 1: "+transferDto.getUserReceiveId());
        log.info("open transaction");
        Transaction transaction = new Transaction();
        if (kindTransaction.equals("transfer")) {
            transaction = moneyTransfer(userDecreaseId, transferDto, kindTransaction);
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
    protected Transaction moneyTransfer(final Long userDecreaseId, final TransferDto transferDto, final String kindTransaction) {
        Transaction error = new Transaction();
        error.setKindTransaction("error");
        log.info("should be id 1 " + transferDto.getUserReceiveId());

        TransferDto returnTransferDto;
        try {
            returnTransferDto = feignServiceAccountsManager.quickTransfer(userDecreaseId, transferDto);
            if (returnTransferDto.getAmount().equals(new BigDecimal(-1))) {
                error.setDescription(returnTransferDto.getUserAccountNumber());
                return error;
            }
        } catch (Exception e) {
            error.setDescription("Failed connecting with accounts manager");
            return error;
        }
        return createMoneyTransferHistory(userDecreaseId, returnTransferDto, kindTransaction,transferDto.getAmount());
    }

    private Transaction createMoneyTransferHistory(final Long userDecreaseId, final TransferDto returnedTransferDto,
                                                   final String kindTransaction, final BigDecimal amount) {
        Transaction transactionForDecreaseUser = new Transaction();
        transactionForDecreaseUser.setUserId(userDecreaseId);
        transactionForDecreaseUser.setKindTransaction(kindTransaction);
        transactionForDecreaseUser.setDescription("Outgoing transfer");
        transactionForDecreaseUser.setValue("-"+amount);
        adapterTransactionRepository.save(transactionMapper.mapToTransactionEntityFromTransaction(transactionForDecreaseUser));

        Transaction transactionForIncreaseUser = new Transaction();
        transactionForIncreaseUser.setUserId(returnedTransferDto.getUserReceiveId());
        transactionForIncreaseUser.setKindTransaction(kindTransaction);
        transactionForIncreaseUser.setDescription("Incoming transfer");
        transactionForIncreaseUser.setValue("+"+amount);
        adapterTransactionRepository.save(transactionMapper.mapToTransactionEntityFromTransaction(transactionForIncreaseUser));
        return transactionForDecreaseUser;
    }
}
