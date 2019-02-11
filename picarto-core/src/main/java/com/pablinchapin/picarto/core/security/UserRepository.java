/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pablinchapin.picarto.core.security;

import com.pablinchapin.picarto.core.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author pvargas
 */


public interface UserRepository extends JpaRepository<User, Long>{
    
    User findByEmail(String email);
    
}
