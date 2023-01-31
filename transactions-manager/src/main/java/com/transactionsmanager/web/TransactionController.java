package com.transactionsmanager.web;

import com.transactionsmanager.service.TransactionService;
import com.transactionsmanager.service.mapper.TransactionMapper;
import com.transactionsmanager.web.dto.TransactionDto;
import com.transactionsmanager.web.dto.TransferDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;

    @PostMapping
    public ResponseEntity<TransactionDto> makeTransaction(@RequestParam Long userId,
                                                          @RequestParam Long thisAccountId,
                                                          @RequestParam String descriptionTransaction,
                                                          @RequestBody TransferDto transferDto) {
        return ResponseEntity.ok(transactionMapper.mapToTransactionDtoFromTransaction
                (transactionService.openTransaction
                        (userId,thisAccountId, descriptionTransaction, transferDto)));
    }
}
