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

    public Transaction openTransaction(final Long thisAccountId, final String descriptionTransaction, final TransferDto transferDto) {
        log.info("should be some id: " + transferDto.getUserReceiveId());
        log.info("open transaction");
        Transaction transaction = new Transaction();
        Transaction error = new Transaction();
        if (descriptionTransaction.equals("transfer money"))
            return moneyTransfer(thisAccountId, transferDto, descriptionTransaction, error);
        /*
        if (descriptionTransaction.equals("deposit") || descriptionTransaction.equals("withdraw"))
            return depositOrWithdraw();

         */


        log.info("wrong kind of transaction");
        transaction.setKindTransaction("error");
        transaction.setDescription("Wrong kind of transaction transactions-manager");
        return transaction;
    }

    @Transactional
    protected Transaction moneyTransfer(final Long thisAccountId, final TransferDto transferDto,
                                        final String descriptionTransaction, Transaction error) {

        error.setKindTransaction("error");
        Transaction transaction = makeMoneyTransfer(thisAccountId, transferDto, descriptionTransaction, error);
        if (transaction.getKindTransaction().equals("error")) {
            log.info("something is wrong with returning transaction");
            transaction.setDescription(transaction.getDescription());
            return transaction;
        }
        return transaction;
    }

    private Transaction makeMoneyTransfer(final Long thisAccountId, final TransferDto transferDto,
                                          final String descriptionTransaction, final Transaction error) {
        TransferDto returnTransferDto;
        try {
            returnTransferDto = feignServiceAccountsManager.quickTransfer(thisAccountId, transferDto);
            if (returnTransferDto.getAmount().equals(new BigDecimal(-1))) {
                error.setDescription(returnTransferDto.getUserAccountNumber());
                return error;
            }
        } catch (Exception e) {
            error.setDescription("Failed connecting with accounts manager");
            return error;
        }
        return closeMoneyTransferTransaction(thisAccountId, returnTransferDto, descriptionTransaction, transferDto.getAmount());
    }

    private Transaction closeMoneyTransferTransaction(final Long thisAccountId, final TransferDto returnedTransferDto,
                                         final String descriptionTransaction, final BigDecimal amount) {
        inComingTransaction(returnedTransferDto.getUserReceiveId(), descriptionTransaction, amount);
        return outGoingTransaction(thisAccountId, descriptionTransaction, amount);
    }

    private Transaction inComingTransaction(final Long userId, final String descriptionTransaction, final BigDecimal amount) {
        Transaction transactionForIncreaseUser = new Transaction();
        transactionForIncreaseUser.setUserId(userId);
        transactionForIncreaseUser.setValue("+" + amount);
        transactionForIncreaseUser.setKindTransaction("Incoming");
        transactionForIncreaseUser.setDescription(descriptionTransaction);
        return transactionMapper.mapToTransactionFromTransactionEntity
                (adapterTransactionRepository.save
                        (transactionMapper.mapToTransactionEntityFromTransaction
                                (transactionForIncreaseUser)));
    }

    private Transaction outGoingTransaction(final Long userId, final String descriptionTransaction, final BigDecimal amount) {
        Transaction transactionForDecreaseUser = new Transaction();
        transactionForDecreaseUser.setUserId(userId);
        transactionForDecreaseUser.setValue("-" + amount);
        transactionForDecreaseUser.setKindTransaction("Outgoing");
        transactionForDecreaseUser.setDescription(descriptionTransaction);
        return transactionMapper.mapToTransactionFromTransactionEntity
                (adapterTransactionRepository.save
                        (transactionMapper.mapToTransactionEntityFromTransaction
                                (transactionForDecreaseUser)));
    }

}
