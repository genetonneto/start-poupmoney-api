package com.web.start.poupmoney.api.poupmoney.services;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.web.start.poupmoney.api.poupmoney.models.User;
import com.web.start.poupmoney.api.poupmoney.repositories.UserRepo;
import com.web.start.poupmoney.api.poupmoney.security.UserSecurity;

@Service
public class UserDetailsServiceImp implements UserDetailsService{

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepo.findByUsername(username);
        if(Objects.isNull(user)) 
            throw new UsernameNotFoundException("Usuario não encontrado: " + username);
        return new UserSecurity(user.getId(), user.getUsername(), user.getPassword(), user.getProfiles());
    }
    
}
