package com.creditmanager.repository;

import com.creditmanager.domain.CreditEntity;
import com.creditmanager.repository.adapter.AdapterCreditEntityRepository;
import org.springframework.data.jpa.repository.JpaRepository;

interface CreditEntityRepository extends AdapterCreditEntityRepository, JpaRepository<CreditEntity, Long> {
}
