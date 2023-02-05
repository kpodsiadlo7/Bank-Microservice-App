package com.transactionsmanager.service;

import com.transactionsmanager.domain.TransactionEntity;
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
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {
    private final FeignServiceAccountsManager feignServiceAccountsManager;
    private final TransactionMapper transactionMapper;
    private final AdapterTransactionRepository adapterTransactionRepository;

    public Set<Transaction> getAllTransactionsByUserId(final Long userId) {
        return transactionMapper.mapToTransactionSetFromTransactionEntitySet
                        (adapterTransactionRepository.findAllByUserId(userId));
    }
    public Transaction openTransaction(final Long userId, final Long thisAccountId,
                                       final String descriptionTransaction, final TransferDto transferDto) {
        log.info("open transaction");
        Transaction transaction = new Transaction();
        Transaction error = new Transaction();
        error.setKindTransaction("error");

        if (descriptionTransaction.equals("transfer money"))
            return moneyTransfer(userId, thisAccountId, transferDto, descriptionTransaction, error, transaction);

        if (descriptionTransaction.equals("deposit") || descriptionTransaction.equals("withdraw"))
            return depositOrWithdraw(userId, thisAccountId, transferDto, descriptionTransaction, error);

        if (descriptionTransaction.equals("credit") || descriptionTransaction.equals("commission")) {
            if (descriptionTransaction.equals("credit"))
                return depositMoney(transferDto.getUserReceiveId(), thisAccountId,
                        transferDto, descriptionTransaction, error);
            return withdrawMoney(transferDto.getUserReceiveId(), thisAccountId,
                    transferDto, descriptionTransaction, error);
        }

        log.info("wrong kind of transaction");
        transaction.setKindTransaction("error");
        transaction.setDescription("Wrong kind of transaction transactions-manager");
        return transaction;
    }

    private Transaction depositOrWithdraw(final Long userId, final Long thisAccountId, final TransferDto transferDto,
                                          final String descriptionTransaction, Transaction error) {
        log.info("open transaction for deposit or withdraw money");
        if (descriptionTransaction.equals("deposit")) {
            transferDto.setAccountNumber("deposit");
            return depositMoney(userId, thisAccountId, transferDto, descriptionTransaction, error);
        }
        return withdrawMoney(userId, thisAccountId, transferDto, descriptionTransaction, error);
    }

    private Transaction depositMoney(final Long userId, final Long thisAccountId, final TransferDto transferDto,
                                     final String descriptionTransaction, Transaction error) {
        log.info("deposit money");
        if (!descriptionTransaction.equals("credit") &&
                transferDto.getAmount().compareTo(BigDecimal.valueOf(15000)) > 0) {
            log.warn("limit 15000");
            error.setDescription("Limit for this kind of transaction is 15000");
            return error;
        }
        try {
            transferDto.setAccountNumber("credit");
            final TransferDto returnTransferDto = feignServiceAccountsManager.depositMoney(thisAccountId, transferDto);
            if (returnTransferDto.getAmount().equals(new BigDecimal(-1))) {
                log.warn("error " + returnTransferDto.getAccountNumber());
                error.setDescription(returnTransferDto.getAccountNumber());
                return error;
            }
        } catch (Exception e) {
            log.warn("failed connecting with accounts manager");
            error.setDescription("Failed connecting with accounts manager");
            return error;
        }
        log.info("successful receive transfer dto from accounts manager");
        return inComingTransaction(userId, thisAccountId, descriptionTransaction, transferDto.getAmount());
    }

    private Transaction withdrawMoney(final Long userId, final Long thisAccountId, final TransferDto transferDto,
                                      final String descriptionTransaction, Transaction error) {
        log.info("withdraw money");
        if (!descriptionTransaction.equals("commission") &&
                transferDto.getAmount().compareTo(BigDecimal.valueOf(5000)) > 0) {
            log.warn("limit 5000");
            error.setDescription("Limit for this kind of transaction is 5000");
            return error;
        }
        try {
            transferDto.setAccountNumber("withdraw");
            final TransferDto returnTransferDto = feignServiceAccountsManager.withdrawMoney(thisAccountId, transferDto);
            log.info("successful receive transfer dto from transfer dto");
            if (returnTransferDto.getAmount().equals(new BigDecimal(-1))) {
                log.warn("error " + returnTransferDto.getAccountNumber());
                error.setDescription(returnTransferDto.getAccountNumber());
                return error;
            }
        } catch (Exception e) {
            error.setDescription("Failed connecting with accounts manager");
            log.warn("failed connecting with accounts mangager");
            return error;
        }
        return outGoingTransaction(userId, thisAccountId, descriptionTransaction, transferDto.getAmount());
    }


    @Transactional
    protected Transaction moneyTransfer(final Long userId, final Long thisAccountId, final TransferDto transferDto,
                                        final String descriptionTransaction, Transaction error,
                                        Transaction transaction) {
        log.info("money transfer");
        if (transferDto.getAmount().compareTo(BigDecimal.valueOf(20000)) > 0) {
            log.warn("limit 20000");
            error.setDescription("Limit for this kind of transaction is 20000");
            return error;
        }
        transaction = makeMoneyTransfer(userId, thisAccountId, transferDto, descriptionTransaction, error);
        if (transaction.getKindTransaction().equals("error")) {
            log.warn("something is wrong with returning transaction");
            transaction.setDescription(transaction.getDescription());
            return transaction;
        }
        return transaction;
    }

    private Transaction makeMoneyTransfer(final Long userId, final Long thisAccountId, final TransferDto transferDto,
                                          final String descriptionTransaction, final Transaction error) {
        log.info("make money transfer");
        TransferDto returnTransferDto;
        try {
            returnTransferDto = feignServiceAccountsManager.quickTransfer(thisAccountId, transferDto);
            if (returnTransferDto.getAmount().equals(new BigDecimal(-1))) {
                error.setDescription(returnTransferDto.getAccountNumber());
                return error;
            }
        } catch (Exception e) {
            log.warn("failed connecting with account manager");
            error.setDescription("Failed connecting with accounts manager");
            return error;
        }
        log.info("successful receive transfer dto from accounts manager");
        return closeMoneyTransferTransaction(userId, thisAccountId, returnTransferDto, descriptionTransaction, transferDto.getAmount());
    }

    private Transaction closeMoneyTransferTransaction(final Long userId, final Long thisAccountId, final TransferDto returnedTransferDto,
                                                      final String descriptionTransaction, final BigDecimal amount) {
        log.info("close money transfer transaction");
        inComingTransaction(returnedTransferDto.getUserReceiveId(), returnedTransferDto.getAccountReceiveId(), descriptionTransaction, amount);
        return outGoingTransaction(userId, thisAccountId, descriptionTransaction, amount);
    }

    private Transaction inComingTransaction(final Long userId, final Long thisAccountId, final String descriptionTransaction, final BigDecimal amount) {
        log.info("incoming transaction");
        log.info("should be id "+thisAccountId);
        Transaction transactionForIncreaseUser = new Transaction();
        transactionForIncreaseUser.setUserId(userId);
        transactionForIncreaseUser.setValue("+" + amount);
        transactionForIncreaseUser.setAccountId(thisAccountId);
        transactionForIncreaseUser.setKindTransaction("Incoming");
        transactionForIncreaseUser.setDescription(descriptionTransaction);
        return transactionMapper.mapToTransactionFromTransactionEntity
                (adapterTransactionRepository.save
                        (transactionMapper.mapToTransactionEntityFromTransaction
                                (transactionForIncreaseUser)));
    }

    private Transaction outGoingTransaction(final Long userId, final Long thisAccountId, final String descriptionTransaction, final BigDecimal amount) {
        log.info("outgoing transaction");
        Transaction transactionForDecreaseUser = new Transaction();
        transactionForDecreaseUser.setUserId(userId);
        transactionForDecreaseUser.setValue("-" + amount);
        transactionForDecreaseUser.setAccountId(thisAccountId);
        transactionForDecreaseUser.setKindTransaction("Outgoing");
        transactionForDecreaseUser.setDescription(descriptionTransaction);
        return transactionMapper.mapToTransactionFromTransactionEntity
                (adapterTransactionRepository.save
                        (transactionMapper.mapToTransactionEntityFromTransaction
                                (transactionForDecreaseUser)));
    }

}
