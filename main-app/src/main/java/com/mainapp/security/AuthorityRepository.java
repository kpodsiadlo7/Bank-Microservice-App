package com.mainapp.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface AuthorityRepository extends AdapterAuthorityRepository, JpaRepository<AuthorityEntity, Long> {
    
}
