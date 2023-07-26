package com.openclassrooms.poseidon.controllers;

import com.openclassrooms.poseidon.domain.Users;
import com.openclassrooms.poseidon.forms.UserForm;
import com.openclassrooms.poseidon.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public String viewHomePage(Model model) {
        model.addAttribute("listUsers", userService.listAll());
        return "indexUser";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/showNewUserForm")
    public String showNewUserForm(Model model) {
        // create model attribute to bind form data
        UserForm userform = new UserForm();
        model.addAttribute("userForm", userform);
        return "newUser";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/saveUser")
    public String saveUser(@Valid @ModelAttribute("userForm") UserForm userForm,
                           BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "newUser";
        }

        Users users = new Users();
        users.setFullName(userForm.getFullName());
        users.setRole(userForm.getRole());
        users.setPassword(userForm.getPassword());
        users.setUsername(userForm.getUsername());
        userService.saveUser(users);
        return "redirect:/";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {

        // get employee from the service
        Users user = userService.get(id);

        // set employee as a model attribute to pre-populate the form
        model.addAttribute("user", user);
        return "updateUser";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/deleteUser/{id}")
    public String deleteEmployee(@PathVariable(value = "id") long id) {

        // call delete employee method
        this.userService.delete(id);
        return "redirect:/";
    }
}

