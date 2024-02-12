package com.example.demo.controller;

import com.example.demo.model.Contact;
import com.example.demo.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class ContactController {

    @Autowired
    ContactService service;

    @RequestMapping(value = "/contact")
    public String getContactPage() {
        return "contact.html";
    }

    @RequestMapping(value = "/saveMsg", method = POST)
    public ModelAndView saveData(Contact contact) {
        service.saveMessageDetails(contact);
        return new ModelAndView("redirect:/contact");
    }
}
