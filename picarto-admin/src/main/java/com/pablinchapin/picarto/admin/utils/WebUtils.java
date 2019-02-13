/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pablinchapin.picarto.admin.utils;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author pvargas
 */
public class WebUtils {

    private WebUtils() {
    
    }

    
    public static final String IMAGES_PREFIX = "/products/images/";
    public static final String IMAGES_DIR = "/picarto/products/";
    
    
    public static String getURLWithContextPath(HttpServletRequest request){
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                        + request.getContextPath();
    }
    
    
    
    
    
    
}
