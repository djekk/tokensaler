package com.syqu.shop.controller;

import com.syqu.shop.event.OnRegistrationCompleteEvent;
import com.syqu.shop.object.Customer;
import com.syqu.shop.object.Distributor;
import com.syqu.shop.object.VerificationToken;
import com.syqu.shop.service.CustomerService;
import com.syqu.shop.service.DistributorService;
import com.syqu.shop.service.UserAlreadyExistException;
import com.syqu.shop.service.VerificationTokenService;
import com.syqu.shop.validator.CustomerValidator;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegisterController {
    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);
    private final CustomerService customerService;
    private final CustomerValidator customerValidator;
    private final DistributorService distributorService;
    private final VerificationTokenService verificationTokenService;
    private final ApplicationEventPublisher eventPublisher;
    
    private String appUrl = "http://localhost:8080";
    @Autowired
    public RegisterController(CustomerService customerService, 
    		CustomerValidator customerValidator,
    		DistributorService distributorService,
    		BCryptPasswordEncoder bCryptPasswordEncoder,
    		VerificationTokenService verificationTokenService, 
    		ApplicationEventPublisher eventPublisher) {
        this.customerService = customerService;
        this.customerValidator = customerValidator;
        this.distributorService = distributorService;
        this.verificationTokenService = verificationTokenService;
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
    		@ModelAttribute("userForm") Customer customerForm, 
    		BindingResult bindingResult, 
    		RedirectAttributes redirectAttributes) 
    {
    	Distributor distributor = distributorService.findByUsername(distr);
    	if( distributor != null )
    	{
    		customerValidator.validate(customerForm, bindingResult);
	        
	        if (bindingResult.hasErrors()) {
	            logger.error(String.valueOf(bindingResult.getFieldError()));
	            return "register";
	        }   
	
	       customerForm.setDistributor(distributor);
	   
	        Customer registered;
			try {
				registered = customerService.registerNewUserAccount(customerForm);				
			   
		            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, 
		            	appUrl));
		            
			} catch (UserAlreadyExistException e) {
				// TODO Auto-generated catch block
			   	return "error/404";   
			}
	
	        redirectAttributes.addFlashAttribute("email", customerForm.getEmail());
	        
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

    @GetMapping("/regitrationConfirm")
    public String confirmRegistration
      (@ModelAttribute("token") String token, Model model) {
        
        VerificationToken verificationToken = verificationTokenService.getVerificationToken(token);
        if (verificationToken == null) {
            model.addAttribute("error", "Sorry invalid token");
            return "redirect:/login?error=" + "Sorry invalid token";
        }
        
        Customer customer = verificationToken.getCustomer();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            model.addAttribute("error", "Sorry token was expired");
            return "redirect:/login?error=" + "Sorry token was expired";
        } 
                
        customer.setEnabled(true); 
        
        String psw = customer.getPassword();
        
        customerService.save(customer); 
                      
        customerService.login(customer.getEmail(), psw);
        
        return "redirect:/home";
    }
}
