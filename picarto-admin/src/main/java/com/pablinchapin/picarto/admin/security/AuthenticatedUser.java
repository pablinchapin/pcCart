/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pablinchapin.picarto.admin.security;

import com.pablinchapin.picarto.core.entities.Permission;
import com.pablinchapin.picarto.core.entities.Role;
import com.pablinchapin.picarto.core.entities.User;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

/**
 *
 * @author pvargas
 */


public class AuthenticatedUser extends org.springframework.security.core.userdetails.User {
    
    
    private static final long serialVersionUID = 1L;
    private User user;

    public AuthenticatedUser(User user) {
        super(user.getEmail(), user.getPassword(), getAuthorities(user));
        this.user = user;
    }
    
    
    public User getUser(){
        return user;
    }

    
    
    private static Collection<? extends GrantedAuthority> getAuthorities(User user){
        
        Set<String> roleAndPermissions = new HashSet<>();
        List<Role> roles = user.getRoles();
        
        for(Role role : roles){
            roleAndPermissions.add(role.getName());
            List<Permission> permissions = role.getPermissions();
            for(Permission permission : permissions){
                roleAndPermissions.add("ROLE_" + permission.getName());
            }
        }
        
        String[] roleNames = new String[roleAndPermissions.size()];
        
        Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(roleAndPermissions.toArray(roleNames));
    
        return authorities;
    }
    
    
    
    
    
    
}
