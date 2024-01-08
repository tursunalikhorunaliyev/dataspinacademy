package com.dataspin.dataspinacademy.security;

import com.dataspin.dataspinacademy.entity.UserData;
import com.dataspin.dataspinacademy.repository.UserDataRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private UserDataRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserData user = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User not found"));

        return new User(user.getUsername(), user.getPassword(), getRoles(user));
    }
    private Set<SimpleGrantedAuthority> getRoles(UserData user){
        return user.getRoles().stream().map(element -> new SimpleGrantedAuthority(element.getName())).collect(Collectors.toSet());
    }



}
