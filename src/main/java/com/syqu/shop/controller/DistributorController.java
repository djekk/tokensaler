package com.syqu.shop.controller;

import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.syqu.shop.object.Customer;
import com.syqu.shop.object.Distributor;
import com.syqu.shop.service.DistributorService;

import net.minidev.json.JSONObject;

@Controller
public class DistributorController {
    private static final Logger logger = LoggerFactory.getLogger(DistributorController.class);

    private final DistributorService distributorService;
    
    @Autowired
    public DistributorController(DistributorService distributorService) {
        this.distributorService = distributorService;
    }

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
    
  /*  @PostMapping("/distributor3")
    public String distributorIn3(@RequestBody LoginDistributorPost ld) {

    	System.out.println(ld.getUsername());
        System.out.println(ld.getPassword());
        System.out.println(ld.getDistributor());
    	    
        return "login";
    }*/
    
    
    @GetMapping("/distributorPage")
    public String register(@ModelAttribute("distr") String distr, Model model) {

    	Distributor distributor = distributorService.findByUsername(distr);
    	if( distributor != null )
    	{
        	model.addAttribute("distr", distr);
            return "distributor";
    	}
    	
    	return "error/404";
    }
}
