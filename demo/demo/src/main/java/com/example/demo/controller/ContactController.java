package com.example.demo.controller;

import com.example.demo.constants.MessageStatus;
import com.example.demo.model.Contact;
import com.example.demo.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class ContactController {

    @Autowired
    ContactService service;

    @RequestMapping(value = "/contact")
    public String getContactPage() {
        return "contact.html";
    }

    @RequestMapping(value = "/saveMsg",method = POST)
    public String saveData(Contact contact) {
        service.saveMessageDetails(contact);
        return "redirect:/contact";
    }

    @RequestMapping(value = "/displayMessages")
    public ModelAndView getMessagePage(Model model) {
        List<Contact> contactList = service.getStatusMessage();
        ModelAndView modelAndView = new ModelAndView("message.html");
        modelAndView.addObject("contactMsgs", contactList);
        return modelAndView;
    }

    @RequestMapping(value = "/closeMsg", method = GET)
    public String updateStatus(@RequestParam int id, Authentication authentication) {
        service.updateStatus(id, authentication.getName());
        return"redirect:/displayMessages";
    }
}
