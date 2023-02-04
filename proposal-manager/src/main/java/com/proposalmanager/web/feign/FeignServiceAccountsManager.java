package com.proposalmanager.web.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "accounts-manager", url = "http://accounts-manager:8010")
public interface FeignServiceAccountsManager {

}
