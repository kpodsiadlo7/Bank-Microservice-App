package com.usermanager.repository;

import com.usermanager.adapter.AdapterUserEntityRepository;
import com.usermanager.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEntityRepository extends AdapterUserEntityRepository, JpaRepository<UserEntity,Long> {

}
