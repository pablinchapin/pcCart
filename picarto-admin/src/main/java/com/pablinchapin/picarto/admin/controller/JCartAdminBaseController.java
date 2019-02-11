/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pablinchapin.picarto.admin.controller;

import com.pablinchapin.picarto.admin.security.AuthenticatedUser;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 *
 * @author pvargas
 */
public abstract class JCartAdminBaseController {
    
    
    
    @Autowired 
    protected MessageSource messageSource;
    
    public String getMessage(String code){
        return messageSource.getMessage(code, null, null);
    }
    
    public String getMessage(String code, String defaultMessage){
        return messageSource.getMessage(code, null, defaultMessage, null);
    }
    
    @ModelAttribute("authenticatedUser")
    public AuthenticatedUser authenticatedUser(@AuthenticationPrincipal AuthenticatedUser authenticatedUser){
        return authenticatedUser;
    }
    
    
    public static AuthenticatedUser getCurrentUser(){
        
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        if(principal instanceof AuthenticatedUser){
            return ((AuthenticatedUser) principal);
        }
    
        return null;
    }
    
    
    public static boolean isLoggedIn(){
        return getCurrentUser() != null;
    }
    
    
}
