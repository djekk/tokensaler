package com.syqu.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(Model model, String error){
        if (error == "")
        {
        	model.addAttribute("error", "Your e-mail and password is invalid.");
        }
        else
        	 if (error != null)
        	 {
        		 model.addAttribute("error", error);
        	 }

        return "login";
    }
}
