/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pablinchapin.picarto.admin.configuration;

import com.pablinchapin.picarto.admin.security.PostAuthorizationFilter;
import javax.servlet.Filter;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

/**
 *
 * @author pvargas
 */

@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {
    
    
    @Value("${server.port:9443}")
    private int serverPort;
    
    @Autowired
    private MessageSource messageSource;
    
    @Autowired
    private PostAuthorizationFilter postAuthorizationFilter;
     
    
    @Override
    public Validator getValidator(){
        
        LocalValidatorFactoryBean factory = new LocalValidatorFactoryBean();
        
        factory.setValidationMessageSource(messageSource);
    
    return factory;
    }
    
    
    @Bean
    public FilterRegistrationBean securityFilterChain(@Qualifier(AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME) Filter securityFilter) {
        FilterRegistrationBean registration = new FilterRegistrationBean(securityFilter);
        registration.setOrder(Integer.MAX_VALUE - 1);
        registration.setName(AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME);
        return registration;
    }

    @Bean
    public FilterRegistrationBean PostAuthorizationFilterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(postAuthorizationFilter);
        registrationBean.setOrder(Integer.MAX_VALUE);
        return registrationBean;
    }
        
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/login")
                    .setViewName("public/login");
        
        registry.addRedirectViewController("/", "/home");
    }
    
    
    @Bean 
    public SpringSecurityDialect securityDialect(){
        return new SpringSecurityDialect();
    }
    
    
    @Bean
    public ClassLoaderTemplateResolver emailTemplateResolver(){
        ClassLoaderTemplateResolver emailTemplateResolver = new ClassLoaderTemplateResolver();
        
        emailTemplateResolver.setPrefix("email/");
        emailTemplateResolver.setSuffix(".html");
        
        emailTemplateResolver.setTemplateMode("HTML5");
        emailTemplateResolver.setCharacterEncoding("UTF-8");
        emailTemplateResolver.setOrder(2);
        
        return emailTemplateResolver;
    }
    
    
    @Bean
    public ServletWebServerFactory servletContainer(){
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory(){
            
            @Override
            protected void postProcessContext(Context context){
                
                SecurityConstraint securityConstraint = new SecurityConstraint();
                securityConstraint.setUserConstraint("CONFIDENTIAL");
                
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                
                securityConstraint.addCollection(collection);
                
                context.addConstraint(securityConstraint);
            
            }
        };
        
        tomcat.addAdditionalTomcatConnectors(redirectConnector());
        
        return tomcat;
    }
    
    
    private Connector redirectConnector(){
    
        Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
        connector.setScheme("http");
        connector.setPort(9090);
        connector.setSecure(false);
        connector.setRedirectPort(serverPort);
        
        return connector;
    }
    
    
    
    
    
    
    
}
