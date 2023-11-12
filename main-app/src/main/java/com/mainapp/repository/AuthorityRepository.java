package com.mainapp.repository;

import com.mainapp.repository.adapter.AdapterAuthorityRepository;
import com.mainapp.security.AuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface AuthorityRepository extends AdapterAuthorityRepository, JpaRepository<AuthorityEntity, Long> {
    
}
