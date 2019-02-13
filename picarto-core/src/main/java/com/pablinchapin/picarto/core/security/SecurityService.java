/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pablinchapin.picarto.core.security;

import com.pablinchapin.picarto.core.common.PicartoException;
import com.pablinchapin.picarto.core.entities.User;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 *
 * @author pvargas
 */

@Service
@Transactional
public class SecurityService {
    
    private final UserRepository userRepository;

    @Autowired
    public SecurityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    
    
    public User findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }
    
    
    public String resetPassword(String email){
        User user = findUserByEmail(email);
        
        if(user == null){
            throw new PicartoException("Invalid emial address");
        }
        
        String uuid = UUID.randomUUID().toString();
        
        user.setPasswordResetToken(uuid);
        
        return uuid;
        
    }
    
    
    public void updatePassword(String email, String token, String password){
        
        User user = findUserByEmail(email);
        
        if(user == null){
            throw new PicartoException("Invalid email address");
        }
        
        if(!StringUtils.hasText(token) || !token.equals(user.getPasswordResetToken())){
            throw new PicartoException("Invalid password reset token");
        }
        
        user.setPassword(password);
        user.setPasswordResetToken(null);
    }
    
    
    public boolean verifiPasswordResetToken(String email, String token){
        
        User user = findUserByEmail(email);
        
        if(user == null){
            throw new PicartoException("Invalid email address");
        }
        
        if(!StringUtils.hasText(token) 
                || !token.equals(user.getPasswordResetToken())
        ){
            return false;
        }
    
        return true;
    }
    
    
    
    
    
}
