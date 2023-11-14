package com.mainapp.security;

public interface AdapterAuthorityRepository {
    AuthorityEntity save(AuthorityEntity userEntity);
    AuthorityEntity findByUserId(long userId);
}
