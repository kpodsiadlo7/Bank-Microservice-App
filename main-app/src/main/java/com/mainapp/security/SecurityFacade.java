package com.mainapp.security;

import com.mainapp.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityFacade {
    private final AdapterAuthorityRepository adapterAuthorityRepository;

    public void setAuthentication(User authorityUser) {
        Authentication authentication = new UsernamePasswordAuthenticationToken
                (authorityUser, null, authorityUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public void saveAuthentication(final AuthorityEntity authorityEntity) {
        adapterAuthorityRepository.save(authorityEntity);
    }
}
