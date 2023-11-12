package com.usermanager.repository;

import com.usermanager.domain.UserEntity;
import com.usermanager.repository.adapter.AdapterUserEntityRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface UserEntityRepository extends AdapterUserEntityRepository, JpaRepository<UserEntity,Long> {
}
