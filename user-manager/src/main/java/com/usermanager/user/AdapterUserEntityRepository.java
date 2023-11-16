package com.usermanager.user;

interface AdapterUserEntityRepository {
    UserEntity save(UserEntity userEntity);

    UserEntity findByUsername(String username);

    boolean existsByUsername(String username);
    UserEntity findById(long userId);
}
