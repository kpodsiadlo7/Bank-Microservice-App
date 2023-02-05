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

    public Transaction openTransaction(final Long userId, final Long thisAccountId,
                                       final String descriptionTransaction, final TransferDto transferDto) {
        log.info("open transaction");
        log.info("user should be 4 from request param " +userId);
        log.info("user should be 4 from transferDto " +transferDto.getUserReceiveId());
        log.info(transferDto.getAccountNumber());
        log.info("account to increase money from transferDto "+transferDto.getAccountReceiveId());
        log.info("account to increase money from transferDto from request param"+thisAccountId);
        log.info("description should be credit "+descriptionTransaction);
        Transaction transaction = new Transaction();
        Transaction error = new Transaction();
        error.setKindTransaction("error");

        if (descriptionTransaction.equals("transfer money"))
            return moneyTransfer(userId, thisAccountId, transferDto, descriptionTransaction, error, transaction);

        if (descriptionTransaction.equals("deposit") || descriptionTransaction.equals("withdraw"))
            return depositOrWithdraw(userId, thisAccountId, transferDto, descriptionTransaction, error);


        if (descriptionTransaction.equals("credit"))
            return makeCredit(transferDto,thisAccountId, descriptionTransaction, error);

        log.info("wrong kind of transaction");
        transaction.setKindTransaction("error");
        transaction.setDescription("Wrong kind of transaction transactions-manager");
        return transaction;
    }

    private Transaction makeCredit(final TransferDto transferDto,final Long thisAccountId, final String descriptionTransaction,
                                   Transaction error) {
        return depositMoney(transferDto.getUserReceiveId(), thisAccountId,
                transferDto,descriptionTransaction,error);
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
        if (transferDto.getAmount().compareTo(BigDecimal.valueOf(15000)) > 0) {
            error.setDescription("Limit for this kind of transaction is 15000");
            return error;
        }
        try {
            log.info("deposit money should be 7 "+thisAccountId);
            transferDto.setAccountNumber("credit");
            final TransferDto returnTransferDto = feignServiceAccountsManager.depositMoney(thisAccountId, transferDto);
            if (returnTransferDto.getAmount().equals(new BigDecimal(-1))) {
                error.setDescription(returnTransferDto.getAccountNumber());
                return error;
            }
        } catch (Exception e) {
            error.setDescription("Failed connecting with accounts manager");
            return error;
        }
        return inComingTransaction(userId, thisAccountId, descriptionTransaction, transferDto.getAmount());
    }

    private Transaction withdrawMoney(final Long userId, final Long thisAccountId, final TransferDto transferDto,
                                      final String descriptionTransaction, Transaction error) {
        if (transferDto.getAmount().compareTo(BigDecimal.valueOf(5000)) > 0) {
            error.setDescription("Limit for this kind of transaction is 5000");
            return error;
        }
        try {
            transferDto.setAccountNumber("withdraw");
            final TransferDto returnTransferDto = feignServiceAccountsManager.withdrawMoney(thisAccountId, transferDto);
            if (returnTransferDto.getAmount().equals(new BigDecimal(-1))) {
                error.setDescription(returnTransferDto.getAccountNumber());
                return error;
            }
        } catch (Exception e) {
            error.setDescription("Failed connecting with accounts manager");
            return error;
        }
        return outGoingTransaction(userId, thisAccountId, descriptionTransaction, transferDto.getAmount());
    }


    @Transactional
    protected Transaction moneyTransfer(final Long userId, final Long thisAccountId, final TransferDto transferDto,
                                        final String descriptionTransaction, Transaction error,
                                        Transaction transaction) {

        if (transferDto.getAmount().compareTo(BigDecimal.valueOf(20000)) > 0) {
            error.setDescription("Limit for this kind of transaction is 20000");
            return error;
        }
        transaction = makeMoneyTransfer(userId, thisAccountId, transferDto, descriptionTransaction, error);
        if (transaction.getKindTransaction().equals("error")) {
            log.info("something is wrong with returning transaction");
            transaction.setDescription(transaction.getDescription());
            return transaction;
        }
        return transaction;
    }

    private Transaction makeMoneyTransfer(final Long userId, final Long thisAccountId, final TransferDto transferDto,
                                          final String descriptionTransaction, final Transaction error) {
        TransferDto returnTransferDto;
        try {
            returnTransferDto = feignServiceAccountsManager.quickTransfer(thisAccountId, transferDto);
            if (returnTransferDto.getAmount().equals(new BigDecimal(-1))) {
                error.setDescription(returnTransferDto.getAccountNumber());
                return error;
            }
        } catch (Exception e) {
            error.setDescription("Failed connecting with accounts manager");
            return error;
        }
        log.info("should be id 1 " + returnTransferDto.getAccountReceiveId());
        return closeMoneyTransferTransaction(userId, thisAccountId, returnTransferDto, descriptionTransaction, transferDto.getAmount());
    }

    private Transaction closeMoneyTransferTransaction(final Long userId, final Long thisAccountId, final TransferDto returnedTransferDto,
                                                      final String descriptionTransaction, final BigDecimal amount) {
        inComingTransaction(userId, returnedTransferDto.getAccountReceiveId(), descriptionTransaction, amount);
        return outGoingTransaction(userId, thisAccountId, descriptionTransaction, amount);
    }

    private Transaction inComingTransaction(final Long userId, final Long thisAccountId, final String descriptionTransaction, final BigDecimal amount) {
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
