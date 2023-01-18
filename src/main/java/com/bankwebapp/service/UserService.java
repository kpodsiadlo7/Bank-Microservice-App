package com.bankwebapp.service;

import com.bankwebapp.domain.User;
import com.bankwebapp.repository.UserRepository;
import com.bankwebapp.security.Authority;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.thymeleaf.util.StringUtils;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(final UserRepository userRepository, final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean validUser(User user, ModelMap modelMap) {
        if (StringUtils.isEmpty(user.getUsername()) ||
                StringUtils.isEmpty(user.getPassword()) ||
                StringUtils.isEmpty(user.getConfirmPassword()) ||
                StringUtils.isEmpty(user.getRealName())) {
            modelMap.put("error","You must fill all fields!");
            return false;
        }
        if (!user.getPassword().equals(user.getConfirmPassword())){
            modelMap.put("error","Passwords don't match");
            return false;
        }

        if (userRepository.existsByPlainPasswordAndUsername(user.getPassword(), user.getUsername())) {
            modelMap.put("error", "User with this login and password already exist!");
            return false;
        }

        user.setPlainPassword(user.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(saveUser(user));
        Authentication authentication = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return true;
    }

    private User saveUser(final User user) {
        Authority authority = new Authority();
        authority.setAuthority("ROLE_USER");
        authority.setUser(user);

        Set<Authority> authorities = new HashSet<>();
        authorities.add(authority);

        user.setAuthorities(authorities);
        return user;
    }
}
