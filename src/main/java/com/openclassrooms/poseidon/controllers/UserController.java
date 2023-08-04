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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

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

        try {
            Users users = new Users();
//            users.setId(Long.valueOf(userForm.getId()));
            users.setFullName(userForm.getFullName());
            users.setRole(userForm.getRole());
            users.setPassword(userForm.getPassword());
            users.setUsername(userForm.getUsername());

            userService.saveUser(users);

            return "redirect:/user";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "An error occurred: " + e.getMessage());
            return "newUser";
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUserUpdate(@PathVariable(value = "id") Long id, Model model) {
        try {
            Optional<Users> userOpt = userService.findById(id);
            if (userOpt.isPresent()) {
                Users user = userOpt.get();
                model.addAttribute("userForm", user);
                return "updateUser";
            } else {
                return "redirect:/user";
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "An error occurred: " + e.getMessage());
            return "redirect:/user";
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/updateUser/{id}")
    public String updateUser(@PathVariable(value = "id") Long id, @Valid @ModelAttribute("userForm") UserForm userForm, BindingResult result) {
        if (result.hasErrors()) {
            return "updateUser";
        }
        try {
            Users updatedUser = new Users();
//            updatedUser.setId(Long.valueOf(userForm.getId()));
            updatedUser.setFullName(userForm.getFullName());
            updatedUser.setRole(userForm.getRole());
            updatedUser.setPassword(userForm.getPassword());
            updatedUser.setUsername(userForm.getUsername());

            userService.newUser(id, updatedUser);
            return "redirect:/user";
        } catch (Exception exception) {
            result.rejectValue("username", "", "error : " + exception.getMessage());
            return "updateUser";
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/deleteUser/{id}")
    public String deleteEmployee(@PathVariable(value = "id") long id) {

        // call delete employee method
        this.userService.delete(id);
        return "redirect:/user";
    }
}

