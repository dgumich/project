package ru.innopolis.university.stc27.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.innopolis.university.stc27.service.UserService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PersonalCabinetController {

    @Autowired
    private UserService userService;


    @GetMapping("/hello")
    public String hello(HttpServletRequest http, Model model){

        model.addAttribute("user", userService.getUser(http.getRemoteUser()));
        return "hello";
    }


    @GetMapping("/aboutus")
    public String aboutus(HttpServletRequest http, Model model){
        model.addAttribute("user", userService.getUser(http.getRemoteUser()));
        return "aboutus";
    }

}
