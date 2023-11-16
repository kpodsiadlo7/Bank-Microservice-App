package com.proposalmanager.transaction;

import com.proposalmanager.transfer.dto.TransferDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionFacade {

    private final FeignServiceTransactionsManager feignServiceTransactionsManager;

    public void makeTransaction(Long userReceiveId, Long accountReceiveId, String descriptionTransaction, TransferDto transferDto) {
        feignServiceTransactionsManager.makeTransaction(userReceiveId, accountReceiveId, descriptionTransaction, transferDto);
    }
}
