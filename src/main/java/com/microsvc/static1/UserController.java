package com.microsvc.static1;


import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;


@RestController
public class UserController {
    @PreAuthorize("hasAuthority('ROLE_USER')")	
    @RequestMapping(value = "/user")
    public Model user(Model model, Principal principal) {
        
        UserDetails currentUser 
          = (UserDetails) ((Authentication) principal).getPrincipal();
        model.addAttribute("username", currentUser.getUsername());
        return model;
    }
}

