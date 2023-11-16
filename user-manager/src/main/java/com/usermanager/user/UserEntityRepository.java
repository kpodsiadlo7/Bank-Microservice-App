package com.usermanager.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface UserEntityRepository extends AdapterUserEntityRepository, JpaRepository<UserEntity,Long> {
}
