package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @RequestMapping("/login")
    public String getLogin(Model model, @RequestParam(required = false) String error,
                           @RequestParam(required = false) String logout) {
        String alertMessage = null;
        if (error != null) {
            alertMessage = "Username/Password entered is not correct!!";
        }
         if(logout != null) {
             alertMessage = "Logout Completed Successfully!!";
         }

         model.addAttribute("alertMessage", alertMessage);
         return "login.html";
    }
}
