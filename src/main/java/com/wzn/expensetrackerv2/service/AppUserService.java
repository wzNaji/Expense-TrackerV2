package com.wzn.expensetrackerv2.service;

import com.wzn.expensetrackerv2.config.repository.AppUserRepository;
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

        // Initialize the user if not present. Brug pass generator til eget pwd.
        if (appUserRepository.count() == 0) {
            AppUser appUser = new AppUser();
            appUser.setUsername("shopowner");
            appUser.setPassword(new BCryptPasswordEncoder().encode("securepassword"));
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

