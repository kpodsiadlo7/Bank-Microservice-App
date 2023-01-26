package com.usermanager.adapter;

import com.usermanager.domain.UserEntity;
import com.usermanager.service.data.User;

public interface AdapterUserEntityRepository {
    boolean existsByUsernameAndPlainPassword(String username, String plainPassword);
    UserEntity save(UserEntity userEntity);
}
