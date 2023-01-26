package com.mainapp.security;

import com.mainapp.service.UserService;
import com.mainapp.service.data.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        //User user = userService.getUserByUserNameFromUserEntityRepository(username);
        //if (user == null)
            //throw new UsernameNotFoundException("User doesn't exist!");
        return new SecurityUser();
    }
}
