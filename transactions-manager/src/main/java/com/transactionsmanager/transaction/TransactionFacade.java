package com.transactionsmanager.transaction;

import com.transactionsmanager.account.AccountFacade;
import com.transactionsmanager.transfer.TransferDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionFacade {
    private final AccountFacade accountFacade;
    private final TransactionFactory transactionFactory;
    private final AdapterTransactionRepository adapterTransactionRepository;

    public Set<TransactionDto> getAllTransactionsByUserId(final Long userId) {
        return transactionFactory.mapToTransactionDtoSetFromTransactionEntitySet(
                        (adapterTransactionRepository.findAllByUserId(userId)));
    }
    public Set<TransactionDto> getTransactionsByAccountId(final Long accountId) {
        return transactionFactory.mapToTransactionDtoSetFromTransactionEntitySet
                (adapterTransactionRepository.findAllByAccountId(accountId));
    }
    public TransactionDto openTransaction(final Long userId, final Long thisAccountId,
                                       final String descriptionTransaction, final TransferDto transferDto) {
        log.info("open transaction");
        if (descriptionTransaction.equals("transfer money"))
            return moneyTransfer(userId, thisAccountId, transferDto, descriptionTransaction,
                    Transaction.builder().withKindTransaction("error").build());

        if (descriptionTransaction.equals("deposit") || descriptionTransaction.equals("withdraw"))
            return depositOrWithdraw(userId, thisAccountId, transferDto, descriptionTransaction,
                    Transaction.builder().withKindTransaction("error").build());

        if (descriptionTransaction.equals("credit") || descriptionTransaction.equals("commission")) {
            if (descriptionTransaction.equals("credit"))
                return depositMoney(transferDto.getUserReceiveId(), thisAccountId,
                        transferDto, descriptionTransaction, Transaction.builder().withKindTransaction("error").build());
            return withdrawMoney(transferDto.getUserReceiveId(), thisAccountId,
                    transferDto, descriptionTransaction, Transaction.builder().withKindTransaction("error").build());
        }

        log.info("wrong kind of transaction");
        return TransactionDto.builder().withKindTransaction("error").withDescription("Wrong kind of transaction transactions-manager").build();
    }

    private TransactionDto depositOrWithdraw(final Long userId, final Long thisAccountId, final TransferDto transferDto,
                                          final String descriptionTransaction, Transaction error) {
        log.info("open transaction for deposit or withdraw money");
        if (descriptionTransaction.equals("deposit")) {
            transferDto.setAccountNumber("deposit");
            return depositMoney(userId, thisAccountId, transferDto, descriptionTransaction, error);
        }
        return withdrawMoney(userId, thisAccountId, transferDto, descriptionTransaction, error);
    }

    private TransactionDto depositMoney(final Long userId, final Long thisAccountId, final TransferDto transferDto,
                                     final String descriptionTransaction, Transaction error) {
        log.info("deposit money");
        if (!descriptionTransaction.equals("credit") &&
                transferDto.getAmount().compareTo(BigDecimal.valueOf(15000)) > 0) {
            log.warn("limit 15000");
            return TransactionDto.builder().withDescription("Limit for this kind of transaction is 15000")
                    .withKindTransaction("error")
                    .build();
        }
        try {
            transferDto.setAccountNumber("credit");
            final TransferDto returnTransferDto = accountFacade.depositMoney(thisAccountId, transferDto);
            if (returnTransferDto.getAmount().equals(new BigDecimal(-1))) {
                log.warn("error " + returnTransferDto.getAccountNumber());
                return TransactionDto.builder().withDescription(returnTransferDto.getAccountNumber())
                        .withKindTransaction("error")
                        .build();
            }
        } catch (Exception e) {
            log.warn("failed connecting with accounts manager");
            return TransactionDto.builder().withDescription("Failed connecting with accounts manager")
                    .withKindTransaction("error")
                    .build();
        }
        log.info("successful receive transfer dto from accounts manager");
        return inComingTransaction(userId, thisAccountId, descriptionTransaction, transferDto.getAmount());
    }

    private TransactionDto withdrawMoney(final Long userId, final Long thisAccountId, final TransferDto transferDto,
                                      final String descriptionTransaction, Transaction error) {
        log.info("withdraw money");
        if (!descriptionTransaction.equals("commission") &&
                transferDto.getAmount().compareTo(BigDecimal.valueOf(5000)) > 0) {
            log.warn("limit 5000");
            return TransactionDto.builder().withDescription("Limit for this kind of transaction is 5000")
                    .withKindTransaction("error")
                    .build();
        }
        try {
            transferDto.setAccountNumber("withdraw");
            final TransferDto returnTransferDto = accountFacade.withdrawMoney(thisAccountId, transferDto);
            log.info("successful receive transfer dto from transfer dto");
            if (returnTransferDto.getAmount().equals(new BigDecimal(-1))) {
                log.warn("error " + returnTransferDto.getAccountNumber());
                return TransactionDto.builder().withDescription(returnTransferDto.getAccountNumber())
                        .withKindTransaction("error")
                        .build();
            }
        } catch (Exception e) {
            log.warn("failed connecting with accounts manager");
            return TransactionDto.builder().withDescription("Failed connecting with accounts manager")
                    .withKindTransaction("error")
                    .build();
        }
        return outGoingTransaction(userId, thisAccountId, descriptionTransaction, transferDto.getAmount());
    }

    TransactionDto moneyTransfer(final Long userId, final Long thisAccountId, final TransferDto transferDto,
                                        final String descriptionTransaction, Transaction error) {
        log.info("money transfer");
        if (transferDto.getAmount().compareTo(BigDecimal.valueOf(20000)) > 0) {
            log.warn("limit 20000");
            return TransactionDto.builder().withDescription("Limit for this kind of transaction is 20000")
                    .withKindTransaction("error")
                    .build();
        }
        TransactionDto transactionDto = makeMoneyTransfer(userId, thisAccountId, transferDto, descriptionTransaction, error);
        if (transactionDto.getKindTransaction().equals("error")) {
            log.warn("something is wrong with returning transaction");
            return TransactionDto.builder().withDescription(transactionDto.getDescription())
                    .withKindTransaction("error")
                    .build();
        }
        return transactionDto;
    }

    private TransactionDto makeMoneyTransfer(final Long userId, final Long thisAccountId, final TransferDto transferDto,
                                          final String descriptionTransaction, final Transaction error) {
        log.info("make money transfer");
        TransferDto returnTransferDto;
        try {
            returnTransferDto = accountFacade.quickTransfer(thisAccountId, transferDto);
            if (returnTransferDto.getAmount().equals(new BigDecimal(-1))) {
                log.warn("return transfer dto ma błąd");
                return TransactionDto.builder()
                        .withDescription(returnTransferDto.getAccountNumber())
                        .withKindTransaction("error")
                        .build();
            }
        } catch (Exception e) {
            log.warn("failed connecting with account manager");
            return TransactionDto.builder().withDescription("Failed connecting with accounts manager")
                    .withKindTransaction("error")
                    .build();
        }
        log.info("successful receive transfer dto from accounts manager");
        return closeMoneyTransferTransaction(userId, thisAccountId, returnTransferDto, descriptionTransaction, transferDto.getAmount());
    }

    private TransactionDto closeMoneyTransferTransaction(final Long userId, final Long thisAccountId, final TransferDto returnedTransferDto,
                                                      final String descriptionTransaction, final BigDecimal amount) {
        inComingTransaction(returnedTransferDto.getUserReceiveId(), returnedTransferDto.getAccountReceiveId(), descriptionTransaction, returnedTransferDto.getAmount());
        return outGoingTransaction(userId, thisAccountId, descriptionTransaction, amount);
    }

    private TransactionDto inComingTransaction(final Long userId, final Long thisAccountId, final String descriptionTransaction, final BigDecimal amount) {
        log.info("incoming transaction");
        log.info("should be id "+thisAccountId);
        Transaction transactionForIncreaseUser = Transaction.builder()
        .withUserId(userId)
        .withValue("+" + amount)
        .withAccountId(thisAccountId)
        .withKindTransaction("Incoming")
        .withDescription(descriptionTransaction).build();
        return transactionFactory.mapToTransactionDtoFromTransactionEntity(
                (adapterTransactionRepository.save
                        (transactionFactory.mapToTransactionEntityFromTransaction
                                (transactionForIncreaseUser))));
    }

    private TransactionDto outGoingTransaction(final Long userId, final Long thisAccountId, final String descriptionTransaction, final BigDecimal amount) {
        log.info("outgoing transaction");
        Transaction transactionForDecreaseUser = Transaction.builder()
        .withUserId(userId)
        .withValue("-" + amount)
        .withAccountId(thisAccountId)
        .withKindTransaction("Outgoing")
        .withDescription(descriptionTransaction).build();
        return transactionFactory.mapToTransactionDtoFromTransactionEntity(
                (adapterTransactionRepository.save
                        (transactionFactory.mapToTransactionEntityFromTransaction
                                (transactionForDecreaseUser))));
    }

}
