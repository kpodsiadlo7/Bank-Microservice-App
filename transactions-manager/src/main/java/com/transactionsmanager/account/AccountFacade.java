package com.transactionsmanager.account;

import com.transactionsmanager.transfer.TransferDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountFacade {
    private final FeignServiceAccountsManager feignServiceAccountsManager;

    public TransferDto depositMoney(final Long thisAccountId, final TransferDto transferDto) {
        return feignServiceAccountsManager.depositMoney(thisAccountId, transferDto);
    }

    public TransferDto withdrawMoney(final Long thisAccountId, final TransferDto transferDto) {
        return feignServiceAccountsManager.withdrawMoney(thisAccountId, transferDto);
    }

    public TransferDto quickTransfer(final Long thisAccountId, final TransferDto transferDto) {
        return feignServiceAccountsManager.quickTransfer(thisAccountId, transferDto);
    }
}
