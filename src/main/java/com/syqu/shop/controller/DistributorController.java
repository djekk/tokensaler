package com.syqu.shop.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.syqu.shop.object.Customer;
import com.syqu.shop.object.Distributor;
import com.syqu.shop.service.CustomerService;
import com.syqu.shop.service.DistributorService;

@Controller
public class DistributorController {
    private static final Logger logger = LoggerFactory.getLogger(DistributorController.class);

    private final DistributorService distributorService;
    private final CustomerService customerService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Autowired
    public DistributorController(
    	DistributorService distributorService,
    	CustomerService customerService,
    	BCryptPasswordEncoder bCryptPasswordEncoder) 
    {
        this.distributorService = distributorService;
        this.customerService = customerService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /*
    @PostMapping("/distributor1/{name}")
    public String distributorIn(@PathVariable("name") String distributor) {
 
    	JSONObject personJsonObject = new JSONObject();
    	 personJsonObject.put("username", "user");
    	 //personJsonObject.put("password", "user");
    	     	    
    	System.out.println(personJsonObject);
    	
        return "redirect:/home";
    }
    
    @PostMapping("/distributor2")
    public String distributorIn1(@RequestBody Map<String, String> param) {
 
    	for(Entry<String, String> map : param.entrySet())
    	{
        	System.out.println(map.getKey() + " : " + map.getValue());
    	}
    
        return "login";
    }
    
   @PostMapping("/distributor3")
    public String distributorIn3(@RequestBody LoginDistributorPost ld) {

    	System.out.println(ld.getUsername());
        System.out.println(ld.getPassword());
        System.out.println(ld.getDistributor());
    	    
        return "login";
    }*/
    
    @GetMapping("/distributorRegister")
    public String register(@ModelAttribute("distr") String distr,
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
    
    @GetMapping("/distributorLogin")
    public String distributorLogin(@ModelAttribute("distr") String distr, Model model) 
    {
    	Distributor distributor = distributorService.findByUsername(distr);
    	if( distributor != null )
    	{
    		model.addAttribute("distr", distr);
    		return "distributor_login";
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
    		  	if (bCryptPasswordEncoder.matches(password, user.getPassword()))
    			{
    				customerService.login(email, password);
    				user.setDistributor(distributor);
    				customerService.saveDistributor(user);
    				return "redirect:/home";
    			}
    		}
    	}
    	
        return "redirect:/distributorLogin?distr="+distr;
    }
}
