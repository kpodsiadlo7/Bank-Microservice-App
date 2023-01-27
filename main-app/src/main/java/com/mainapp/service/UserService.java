package com.mainapp.service;

import com.mainapp.repository.adapter.AdapterAuthorityRepository;
import com.mainapp.security.Authority;
import com.mainapp.service.data.User;
import com.mainapp.service.mapper.UserMapper;
import com.mainapp.web.dto.UserDto;
import com.mainapp.web.feign.FeignServiceUserManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {


    private final PasswordEncoder passwordEncoder;
    private final FeignServiceUserManager feignServiceUserManager;
    private final UserMapper userMapper;
    private final AdapterAuthorityRepository adapterAuthorityRepository;

    public boolean createUser(final User user, final ModelMap modelMap) {
       /* if (StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPassword()) || StringUtils.isEmpty(user.getConfirmPassword()) || StringUtils.isEmpty(user.getRealName())) {
            modelMap.put("error", "You must fill all fields!");
            return false;
        }
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            modelMap.put("error", "Passwords don't match");
            return false;
        }
        encodeUserPassword(user);

        */
        User userFromUserManager = userMapper.mapToUserFromUserDto(feignServiceUserManager.createUser(userMapper.mapToUserDtoFromUser(user)));

        Authority authority = new Authority();
        authority.setAuthority("ROLE_USER");
        authority.setUserId(userFromUserManager.getUserId());
        adapterAuthorityRepository.save(authority);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info(authority.getUserId().toString());
        return true;
    }

    private void encodeUserPassword(final User user) {
        user.setPlainPassword(user.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }
}
