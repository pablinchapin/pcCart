/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pablinchapin.picarto.admin.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 *
 * @author pvargas
 */

@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {
    
    @Autowired
    private MessageSource messageSource;
     
    
    public Validator getValidator(){
        
        LocalValidatorFactoryBean factory = new LocalValidatorFactoryBean();
        
        factory.setValidationMessageSource(messageSource);
    
    return factory;
    }
    
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/login")
                    .setViewName("public/login");
        
        registry.addRedirectViewController("/", "/home");
    }
    
}
