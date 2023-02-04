package com.creditmanager.web;

import com.creditmanager.domain.enums.CreditKind;
import com.creditmanager.service.CreditService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CreditController {

    private final CreditService creditService;

    @GetMapping("/check-credit")
    public ResponseEntity<Boolean> checkAccountAlreadyHaveThatKindCredit(@RequestParam Long accountId,
                                                                         @RequestParam CreditKind creditKind) {
        return ResponseEntity.ok(creditService.checkAccountAlreadyHaveThatKindCredit(accountId,creditKind));
    }
}
