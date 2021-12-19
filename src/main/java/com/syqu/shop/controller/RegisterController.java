package com.syqu.shop.controller;

import com.syqu.shop.object.Customer;
import com.syqu.shop.object.Distributor;
import com.syqu.shop.service.CustomerService;
import com.syqu.shop.service.DistributorService;
import com.syqu.shop.validator.CustomerValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public RegisterController(CustomerService customerService, 
    		CustomerValidator customerValidator,
    		DistributorService distributorService) {
        this.customerService = customerService;
        this.customerValidator = customerValidator;
        this.distributorService = distributorService;
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
	        customerService.save(userForm);
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
    
    @PostMapping("/distributorRegister")
    public String distributorRegister(@ModelAttribute("distr") String distr,
    		RedirectAttributes redirectAttributes)
    {
    	Distributor distributor = distributorService.findByUsername(distr);
    	if( distributor != null )
    	{
    		redirectAttributes.addFlashAttribute("distr", distr);
    		return "redirect:/register";
    	}
    	
        return "error/404";
    }
    
    @PostMapping("/distributorLogin")
    public String distributorLogin(
    		@ModelAttribute("email") String email,
    		@ModelAttribute("password") String password,
    		@ModelAttribute("distr") String distr)
    {
    	Distributor distributor = distributorService.findByUsername(distr);
    	if( distributor != null )
    	{    		
    		Customer user = customerService.findByEmail(email);
    		if(user != null && password != null)
    		{
    			if(user.getPasswordConfirm().equals(password))
    			{
    				customerService.login(email, password);
    				user.setDistributor(distributor);
    				customerService.save(user);
    				return "redirect:/home";
    			}
    		}
    	}
    	
        return "redirect:/distributorPage?distr="+distr;
    }
}
