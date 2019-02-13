/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pablinchapin.picarto.admin.controller;

import static com.pablinchapin.picarto.admin.utils.MessageCodes.*;
import com.pablinchapin.picarto.admin.utils.WebUtils;
import com.pablinchapin.picarto.core.common.EmailService;
import com.pablinchapin.picarto.core.common.PicartoException;
import com.pablinchapin.picarto.core.security.SecurityService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 *
 * @author pvargas
 */

@Controller
public class UserAuthController extends JCartAdminBaseController{
    
    private static final String viewPrefix = "public/";
    
    @Autowired
    protected SecurityService securityService;
    
    @Autowired
    protected EmailService emailService;
    
    @Autowired
    protected PasswordEncoder passwordEncoder;
    
    @Autowired
    protected TemplateEngine templateEngine;

    @Override
    protected String getHeaderTitle() {
        return "User";
    }
    
    
    @RequestMapping(
            value = "/forgotPwd",
            method = RequestMethod.GET
    )public String forgotPwd(){
        return viewPrefix+"forgotPwd";
    }
    
    
    @RequestMapping(
            value = "/forgotPwd",
            method = RequestMethod.POST
    )public String handleForgotPassword(
            HttpServletRequest request,
            RedirectAttributes redirectAttributes
    ){
        String email = request.getParameter("email");
        
        
        try{
            String token = securityService.resetPassword(email);
            String resetPwdURL = WebUtils.getURLWithContextPath(request)+"/resetPwd?email="+email+"&token="+token;
            
            logger.debug(resetPwdURL);
            
            this.sendForgotPasswordEmail(email, resetPwdURL);
            
            redirectAttributes.addFlashAttribute("msg", getMessage(INFO_PASSWORD_RESET_LINK_SENT));
            
        }catch(PicartoException e){
            logger.error(e);
            redirectAttributes.addFlashAttribute("msg", e.getMessage());
        }
        
        return "redirect:/forgotPwd";
    }
    
    
    @RequestMapping(
            value = "/resetPwd",
            method = RequestMethod.GET
    )
    public String resetPwd(
            HttpServletRequest request,
            Model model,
            RedirectAttributes redirectAttributes
    ){
        
        String email = request.getParameter("email");
        String token = request.getParameter("token");
        
        boolean valid = securityService.verifiPasswordResetToken(email, token);
        
        if(valid){
            model.addAttribute("email", email);
            model.addAttribute("token", token);
            
            return viewPrefix+"resetPwd";
        }else{
                redirectAttributes.addFlashAttribute("msg", getMessage(ERROR_INVALID_PASSWORD_RESET_REQUEST));
                
                return "redirect:/login";
        }
    
    }
    
    
    @RequestMapping(
            value = "/resetPwd",
            method = RequestMethod.POST
    )
    public String handleResetPwd(
            HttpServletRequest request,
            Model model,
            RedirectAttributes redirectAttributes
    ){
        
        try{
            String email = request.getParameter("email");
            String token = request.getParameter("token");
            String password = request.getParameter("password");
            String confPassword = request.getParameter("confPassword");
            
            if(!password.equals(confPassword)){
                model.addAttribute("email", email);
                model.addAttribute("token", token);
                model.addAttribute("msg", getMessage(ERROR_PASSWORD_CONF_PASSWORD_MISMATCH));
                
                return viewPrefix+"resetPwd";
            }
            
            String encodedPwd = passwordEncoder.encode(password);
            securityService.updatePassword(email, token, encodedPwd);
            
            redirectAttributes.addFlashAttribute("msg", getMessage(ERROR_INVALID_PASSWORD_RESET_REQUEST));
            
        }catch(PicartoException e){
                logger.error(e);
                redirectAttributes.addFlashAttribute("msg", getMessage(INFO_PASSWORD_UPDATED_SUCCESS));
        }
    
        return "redirect:/login";
    }
    
    
    
    protected void sendForgotPasswordEmail(String email, String resetPwdURL){
        
        try {
            final Context ctx = new Context();
            ctx.setVariable("resetPwdURL", resetPwdURL);
            
            final String htmlContent = this.templateEngine.process("forgot-password-email", ctx);
            
            emailService.sendEmail(email, getMessage(LABEL_PASSWORD_RESET_EMAIL_SUBJECT), htmlContent);
            
        }catch(PicartoException e){
                logger.error(e);
        }
        
        
    }
    
    
    
    
}
