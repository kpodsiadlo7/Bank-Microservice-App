package com.mainapp.repository.adapter;

import com.mainapp.security.AuthorityEntity;

public interface AdapterAuthorityRepository {
    AuthorityEntity save(AuthorityEntity userEntity);
}
