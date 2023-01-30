package com.transactionsmanager.service.mapper;

import com.transactionsmanager.service.data.Transfer;
import com.transactionsmanager.web.dto.TransferDto;
import org.springframework.stereotype.Service;

@Service
public class TransferMapper {
    public TransferDto mapToTransferDtoFromTransfer(final Transfer transfer) {
        return new TransferDto(
                transfer.getAmount(),
                transfer.getUserAccountNumber()
        );
    }

    public Transfer mapToTransferFromTransferDto(final TransferDto transferDto) {
        return new Transfer(
                transferDto.getAmount(),
                transferDto.getUserAccountNumber()
        );
    }
}
