package com.creditmanager.credit;

import org.springframework.data.jpa.repository.JpaRepository;

interface CreditEntityRepository extends AdapterCreditEntityRepository, JpaRepository<CreditEntity, Long> {
}
