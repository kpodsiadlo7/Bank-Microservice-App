package com.transactionsmanager.transaction;

import com.transactionsmanager.transaction.dto.TransactionDto;
import com.transactionsmanager.transfer.dto.TransferDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Slf4j
@RestController
@RequiredArgsConstructor
class TransactionController {

    private final TransactionFacade transactionFacade;


    @GetMapping
    ResponseEntity<Set<TransactionDto>> getAllUserTransactionsByUserId(@RequestParam Long userId){
        return ResponseEntity.ok(transactionFacade.getAllTransactionsByUserId(userId));
    }
    @GetMapping("/transactions_by_account_id")
    ResponseEntity<Set<TransactionDto>> getTransactionsByAccountId(@RequestParam Long accountId){
        return ResponseEntity.ok((transactionFacade.getTransactionsByAccountId(accountId)));
    }

    @PostMapping
    ResponseEntity<TransactionDto> makeTransaction(@RequestParam Long userId,
                                                          @RequestParam Long thisAccountId,
                                                          @RequestParam String descriptionTransaction,
                                                          @RequestBody TransferDto transferDto) {
        return ResponseEntity.ok(transactionFacade.openTransaction
                        (userId, thisAccountId, descriptionTransaction, transferDto));
    }

}
