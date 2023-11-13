package com.creditmanager.credit;

import com.creditmanager.credit.enums.CreditKind;
import com.creditmanager.proposal.ProposalDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
class CreditController {

    private final CreditFacade creditFacade;

    @GetMapping("/check-credit")
    ResponseEntity<Boolean> checkAccountAlreadyHaveThatKindCredit(@RequestParam Long accountId,
                                                                  @RequestParam CreditKind creditKind) {
        return ResponseEntity.ok(creditFacade.checkAccountAlreadyHaveThatKindCredit(accountId,creditKind));
    }

    @PostMapping("/accept-credit")
    ResponseEntity<Boolean> acceptCredit(@RequestBody ProposalDto proposalDto){
        return ResponseEntity.ok(creditFacade.acceptCredit(proposalDto));
    }
}
