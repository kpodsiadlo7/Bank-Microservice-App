package com.accountsmanager.transfer;

import com.accountsmanager.transfer.dto.TransferDto;
import org.springframework.stereotype.Service;

@Service
class TransferMapper {
    Transfer mapToTransferFromTransferDto(final TransferDto transferDto) {
        return new Transfer(
                transferDto.getUserReceiveId(),
                transferDto.getAccountReceiveId(),
                transferDto.getAmount(),
                transferDto.getAccountNumber()
        );
    }
    TransferDto mapToUserTransferDtoFromUserTransfer(final Transfer transfer){
        return TransferDto.builder()
                .withUserReceiveId(transfer.getUserReceiveId())
                .withAccountNumber(transfer.getAccountNumber())
                .withAmount(transfer.getAmount())
                .withAccountReceiveId(transfer.getAccountReceiveId()).build();
    }
}
