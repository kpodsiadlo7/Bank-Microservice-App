package com.usermanager.repository.adapter;

import com.usermanager.domain.UserEntity;

public interface AdapterUserEntityRepository {
    boolean existsByUsernameAndPlainPassword(String username, String plainPassword);

    UserEntity save(UserEntity userEntity);
    UserEntity findByUsername(String username);
}
