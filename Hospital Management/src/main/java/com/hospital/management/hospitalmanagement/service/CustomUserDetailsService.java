package com.hospital.management.hospitalmanagement.service;

import com.hospital.management.hospitalmanagement.model.CustomUserDetails;
import com.hospital.management.hospitalmanagement.model.User;
import com.hospital.management.hospitalmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User doesn't exist" + username));
        CustomUserDetails userDetails = new CustomUserDetails();

        userDetails.setUser(user);

        return userDetails;
    }
}
