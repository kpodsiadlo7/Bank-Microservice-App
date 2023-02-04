package com.mainapp.web.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "proposal-manager", url = "http://proposal-manager:8500")
public interface FeignServiceProposalManager {
}
