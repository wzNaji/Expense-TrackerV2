package com.wzn.expensetrackerv2.service.implementation;

import com.wzn.expensetrackerv2.repository.AppUserRepository;
import com.wzn.expensetrackerv2.entity.AppUser;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Service
public class AppUserServiceImpl implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    public AppUserServiceImpl(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;

        // Initialize the user if not present.
        if (appUserRepository.count() == 0) {
            AppUser appUser = new AppUser();
            appUser.setUsername(System.getenv("USERNAMEAZURE"));
            appUser.setPassword(new BCryptPasswordEncoder().encode(System.getenv("USERPWDAZURE")));
            appUserRepository.save(appUser);
        }
    }

    // Load user by username for authentication
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByUsername(username);
        if (appUser == null) throw new UsernameNotFoundException("User not found");

        return new org.springframework.security.core.userdetails.User(
                appUser.getUsername(),
                appUser.getPassword(),
                Collections.emptyList()
        );
    }
}

