package com.creditmanager.web;

import com.creditmanager.domain.enums.CreditKind;
import com.creditmanager.service.CreditService;
import com.creditmanager.service.data.Proposal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CreditController {

    private final CreditService creditService;

    @GetMapping("/check-credit")
    public ResponseEntity<Boolean> checkAccountAlreadyHaveThatKindCredit(@RequestParam Long accountId,
                                                                         @RequestParam CreditKind creditKind) {
        return ResponseEntity.ok(creditService.checkAccountAlreadyHaveThatKindCredit(accountId,creditKind));
    }

    @PostMapping("/accept-credit")
    public ResponseEntity<Boolean> acceptCredit(@RequestBody Proposal proposal){
        return ResponseEntity.ok(creditService.acceptCredit(proposal));
    }
}
