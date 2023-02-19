package com.transactionsmanager.web;

import com.transactionsmanager.service.TransactionService;
import com.transactionsmanager.service.mapper.TransactionMapper;
import com.transactionsmanager.web.dto.TransactionDto;
import com.transactionsmanager.web.dto.TransferDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;


    @GetMapping
    public ResponseEntity<Set<TransactionDto>> getAllUserTransactionsByUserId(@RequestParam Long userId){
        return ResponseEntity.ok(transactionMapper.mapToTransactionDtoSetFromTransactionSet
                (transactionService.getAllTransactionsByUserId(userId)));
    }
    @GetMapping("/transactions_by_account_id")
    public ResponseEntity<Set<TransactionDto>> getTransactionsByAccountId(@RequestParam Long accountId){
        return ResponseEntity.ok(transactionMapper.mapToTransactionDtoSetFromTransactionSet
                (transactionService.getTransactionsByAccountId(accountId)));
    }

    @PostMapping
    public ResponseEntity<TransactionDto> makeTransaction(@RequestParam Long userId,
                                                          @RequestParam Long thisAccountId,
                                                          @RequestParam String descriptionTransaction,
                                                          @RequestBody TransferDto transferDto) {
        return ResponseEntity.ok(transactionMapper.mapToTransactionDtoFromTransaction
                (transactionService.openTransaction
                        (userId, thisAccountId, descriptionTransaction, transferDto)));
    }

}
