package com.accountsmanager.service.transfer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransferFacade {

    private final TransferMapper transferMapper;

    public boolean depositMoney(TransferDto transferDto) {
        log.info("deposit money before validation");
        return (!Objects.equals(validateDataBeforeTransaction(transferDto).getAmount(), new BigDecimal(-1)));
    }

    public boolean withdrawMoney(TransferDto transferDto) {
        log.info("withdraw money");
        return !Objects.equals(validateDataBeforeTransaction(transferDto).getAmount(), new BigDecimal(-1));
    }


    public TransferDto moneyTransferFromUserToUser(final Long thisAccountId, final Long userIncreaseId, final TransferDto transferDto) {
        log.info("money transfer from user to user");
        if (!Objects.equals(validateDataBeforeTransaction(transferDto).getAmount(), new BigDecimal(-1)))
            return closeMoneyTransfer(thisAccountId,userIncreaseId, transferDto);
        return transferDto;
    }

    public TransferDto validateDataBeforeTransaction(TransferDto transferDto) {
        //this case is for taking credit
        if (transferDto.getAccountNumber().equals("credit") || transferDto.getAccountNumber().equals("commission"))
            return transferDto;
        Transfer transfer = transferMapper.mapToTransferFromTransferDto(transferDto);
        if (transferDto.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            log.info("amount min 0");
            transfer.setAccountNumber("Minimum amount must be over 0");
            transfer.setAmount(new BigDecimal(-1));
            return transferMapper.mapToUserTransferDtoFromUserTransfer(transfer);
        }
        log.info("end of validation");
        return transferMapper.mapToUserTransferDtoFromUserTransfer(transfer);
    }

    public TransferDto closeMoneyTransfer(Long thisAccountId, Long userReceiverId, TransferDto transferDto) {
        log.info("close money transfer");
        Transfer transfer = transferMapper.mapToTransferFromTransferDto(transferDto);
        transfer.setAccountReceiveId(thisAccountId);
        transfer.setUserReceiveId(userReceiverId);
        return transferMapper.mapToUserTransferDtoFromUserTransfer(transfer);
    }
}

