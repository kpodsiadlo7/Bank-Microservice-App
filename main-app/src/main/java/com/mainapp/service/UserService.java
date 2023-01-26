package com.mainapp.service;

import com.mainapp.service.data.User;
import com.mainapp.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public boolean validUserData(final User user, final ModelMap modelMap) {
        if (StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPassword()) || StringUtils.isEmpty(user.getConfirmPassword()) || StringUtils.isEmpty(user.getRealName())) {
            modelMap.put("error", "You must fill all fields!");
            return false;
        }
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            modelMap.put("error", "Passwords don't match");
            return false;
        }

        //if (adapterUserEntityRepository.existsByPlainPasswordAndUsername(user.getPassword(), user.getUsername())) {
        // modelMap.put("error", "User with this login and password already exist!");
        // return false;
        //}
        encodeUserPassword(user);
        //UserEntity userEntity = userMapper.mapToUserEntityFromUser(user);
        //adapterUserEntityRepository.save(saveUser(user, userEntity));
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return true;
    }

    private void encodeUserPassword(final User user) {
        user.setPlainPassword(user.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }
}
