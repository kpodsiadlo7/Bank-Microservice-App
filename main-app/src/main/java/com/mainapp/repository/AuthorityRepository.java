package com.mainapp.repository;

import com.mainapp.repository.adapter.AdapterAuthorityRepository;
import com.mainapp.security.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends AdapterAuthorityRepository, JpaRepository<Authority, Long> {
}
