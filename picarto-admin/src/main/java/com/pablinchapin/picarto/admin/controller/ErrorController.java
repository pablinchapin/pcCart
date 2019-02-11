/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pablinchapin.picarto.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author pvargas
 */

@Controller
public class ErrorController {
    
    private static final String viewPrefix = "error/";
    
    @RequestMapping("/403")
    public String accessDenied(){
        return viewPrefix + "accessDenied";
    }
    
}
