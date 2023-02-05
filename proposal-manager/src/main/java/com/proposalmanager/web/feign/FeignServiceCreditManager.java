package com.proposalmanager.web.feign;

import com.proposalmanager.domain.enums.CreditKind;
import com.proposalmanager.service.data.Proposal;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "credit-manager", url = "http://credit-manager:8700")
public interface FeignServiceCreditManager {

    @GetMapping("/check-credit")
    boolean checkAccountAlreadyHaveThatKindCredit(@RequestParam Long accountId,
                                                  @RequestParam CreditKind creditKind);

    @PostMapping("/accept-credit")
    boolean acceptCredit(@RequestBody Proposal proposalToApprove);
}
