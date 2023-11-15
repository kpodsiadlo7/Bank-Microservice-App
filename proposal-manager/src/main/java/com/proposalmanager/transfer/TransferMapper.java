package com.proposalmanager.transfer;

import org.springframework.stereotype.Service;

@Service
public class TransferMapper {
    public TransferDto mapToTransferDtoFromTransfer(final Transfer transfer) {
        return new TransferDto(
                transfer.getUserReceiveId(),
                transfer.getAccountReceiveId(),
                transfer.getAmount(),
                transfer.getAccountNumber()
        );
    }
}
