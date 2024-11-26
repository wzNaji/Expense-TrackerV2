package com.wzn.expensetrackerv2.service.implementation;

import com.wzn.expensetrackerv2.repository.AppUserRepository;
import com.wzn.expensetrackerv2.entity.AppUser;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Service
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    public AppUserService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;

        // Initialize the user if not present.
        if (appUserRepository.count() == 0) {
            AppUser appUser = new AppUser();
            appUser.setUsername(System.getenv("USERNAME"));
            appUser.setPassword(new BCryptPasswordEncoder().encode(System.getenv("USERPWD")));
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

    // Additional methods if needed
}

