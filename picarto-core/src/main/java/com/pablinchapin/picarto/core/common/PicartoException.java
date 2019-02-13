/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pablinchapin.picarto.core.common;

/**
 *
 * @author pvargas
 */
public class PicartoException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    public PicartoException() {
        super();
    }

    public PicartoException(String message) {
        super(message);
    }

    public PicartoException(String message, Throwable cause) {
        super(message, cause);
    }

    public PicartoException(Throwable cause) {
        super(cause);
    }

    public PicartoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
    
}
