package com.transactionsmanager.web;

import com.transactionsmanager.web.dto.TransactionDto;
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

    @PostMapping
    public ResponseEntity<TransactionDto> quickTransferTransaction(@RequestParam Long userId,
                                                                   @RequestParam String kindTransaction,
                                                                   @RequestBody TransactionDto transactionDto) {
        log.info("User id: " +userId);
        log.info("Kind transaction: " +kindTransaction);
        return ResponseEntity.ok().build();
    }

}
