/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pablinchapin.picarto.admin.security;

import com.pablinchapin.picarto.core.entities.User;
import com.pablinchapin.picarto.core.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;



/**
 *
 * @author pvargas
 */

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {
    
    private SecurityService securityService;
    
    @Autowired
    public CustomUserDetailsService(SecurityService securityService) {
        this.securityService = securityService;
    }
    

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        User user = securityService.findUserByEmail(username);
        
        if(user == null){
            throw new UsernameNotFoundException("Email " +username+ " nto found");
        }
        
        return new AuthenticatedUser(user);
    }

    
    
}
