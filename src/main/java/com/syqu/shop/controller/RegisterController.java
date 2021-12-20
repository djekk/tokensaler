package com.syqu.shop.controller;

import com.syqu.shop.event.OnRegistrationCompleteEvent;
import com.syqu.shop.object.Customer;
import com.syqu.shop.object.Distributor;
import com.syqu.shop.object.VerificationToken;
import com.syqu.shop.service.CustomerService;
import com.syqu.shop.service.DistributorService;
import com.syqu.shop.service.UserAlreadyExistException;
import com.syqu.shop.validator.CustomerValidator;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegisterController {
    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);
    private final CustomerService customerService;
    private final CustomerValidator customerValidator;
    private final DistributorService distributorService;
    private final ApplicationEventPublisher eventPublisher;
    
    private String appUrl = "http://localhost:8080";
    @Autowired
    public RegisterController(CustomerService customerService, 
    		CustomerValidator customerValidator,
    		DistributorService distributorService,
    		BCryptPasswordEncoder bCryptPasswordEncoder,
    		ApplicationEventPublisher eventPublisher) {
        this.customerService = customerService;
        this.customerValidator = customerValidator;
        this.distributorService = distributorService;
        this.eventPublisher = eventPublisher;
    }

    @GetMapping("/register")
    public String register(@ModelAttribute("distr") String distr, Model model) {

    	Distributor distributor = distributorService.findByUsername(distr);
    	if( distributor != null )
    	{
        	model.addAttribute("distr", distr);
    		model.addAttribute("userForm", new Customer());
            return "register";
    	}
    	
    	return "error/404";
    }

    @PostMapping("/register")
    public String register(
    		@ModelAttribute("distr") String distr,
    		@ModelAttribute("userForm") Customer userForm, 
    		BindingResult bindingResult, 
    		RedirectAttributes redirectAttributes) 
    {
    	Distributor distributor = distributorService.findByUsername(distr);
    	if( distributor != null )
    	{
    		customerValidator.validate(userForm, bindingResult);
	        
	        if (bindingResult.hasErrors()) {
	            logger.error(String.valueOf(bindingResult.getFieldError()));
	            return "register";
	        }   
	
	        userForm.setDistributor(distributor);
	   
	        Customer registered;
			try {
				registered = customerService.registerNewUserAccount(userForm);				
			   
		            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, 
		            	appUrl));
		            
			} catch (UserAlreadyExistException e) {
				// TODO Auto-generated catch block
			   	return "error/404";   
			}
            
	        ///customerService.save(userForm);
	     //   customerService.login(userForm.getEmail(), userForm.getPasswordConfirm());
	       // return "redirect:/home";
	
	        redirectAttributes.addFlashAttribute("email", userForm.getEmail());
	        
	        return "redirect:/registration";
    	}
    	return "error/404";    	
    }
    
    @GetMapping("/registration")
    public String registration(@ModelAttribute("email") String email, Model model)
    {
    	Customer customer = customerService.findByEmail(email);
    	if( customer != null )
    	{
    		model.addAttribute("user", customer);
    		return "registration";
    	}
    	return "error/404";
    }  
    /*
    @PostMapping("/user/registration")
    public ModelAndView registerUserAccount(
      @ModelAttribute("user") @Valid Customer userDto, 
      HttpServletRequest request, Errors errors) { 
        
        try {
            Customer registered = customerService.registerNewUserAccount(userDto);
            
            String appUrl = request.getContextPath();
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, 
            	appUrl));
        } catch (UserAlreadyExistException uaeEx) {
            ModelAndView mav = new ModelAndView("registration", "user", userDto);
            mav.addObject("message", "An account for that username/email already exists.");
            return mav;
        } catch (RuntimeException ex) {
            return new ModelAndView("emailError", "user", userDto);
        }

        return new ModelAndView("successRegister", "user", userDto);
    }
    */
    @GetMapping("/regitrationConfirm")
    public String confirmRegistration
      (@ModelAttribute("token") String token, Model model) {
        
        VerificationToken verificationToken = customerService.getVerificationToken(token);
        if (verificationToken == null) {
            model.addAttribute("error", "Sorry invalid token");
            return "redirect:/login?error=" + "Sorry invalid token";
        }
        
        Customer user = verificationToken.getCustomer();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            model.addAttribute("error", "Sorry token was expired");
            return "redirect:/login?error=" + "Sorry token was expired";
        } 
                
        user.setEnabled(true); 
        
        String psw = user.getPassword();
        
        customerService.save(user); 
              
        System.out.println("*****psw=" + psw);
        
        customerService.login(user.getEmail(), psw);
        
        return "redirect:/home";
    }
}
