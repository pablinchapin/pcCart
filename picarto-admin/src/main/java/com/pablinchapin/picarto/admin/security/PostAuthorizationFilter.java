/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pablinchapin.picarto.admin.security;

import com.pablinchapin.picarto.core.security.SecurityService;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 *
 * @author pvargas
 */

@Component
public class PostAuthorizationFilter extends OncePerRequestFilter {
    
    @Autowired
    SecurityService securityService;
    
    protected String[] IGNORE_URIS = { "/assets/" };

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain fc) throws ServletException, IOException {
        String uri = request.getRequestURI();
        
        if(!isIgnorableURI(uri)){
            String menu = MenuConfiguration.getMatchingMenu(uri);
            
            request.setAttribute("CURRENT_MENU", menu);
        }
        
        fc.doFilter(request, response);
    }
    
    
    private boolean isIgnorableURI(String uri){
        for(String u : IGNORE_URIS){
            if(uri.startsWith(u)){
                return true;
            }
        }
        
        return false;
    }
    
}
