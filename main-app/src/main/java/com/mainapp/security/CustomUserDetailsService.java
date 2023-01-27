package com.mainapp.security;

import com.mainapp.service.data.User;
import com.mainapp.service.mapper.UserMapper;
import com.mainapp.web.feign.FeignServiceUserManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final FeignServiceUserManager feignServiceUserManager;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        User user = userMapper.mapToUserFromUserDto(feignServiceUserManager.loginUser(username));
        if (user == null)
            throw new UsernameNotFoundException("User doesn't exist!");
        return new SecurityUser(user);
    }
}
