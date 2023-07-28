package com.openclassrooms.poseidon.controllers;

import com.openclassrooms.poseidon.domain.Users;
import com.openclassrooms.poseidon.forms.UserForm;
import com.openclassrooms.poseidon.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;


    @Test
    @WithMockUser(roles = "ADMIN")
    public void testViewHomePage() throws Exception {
        List<Users> userList = new ArrayList<>();
        Users users = new Users(1L, "Hamza", "ben", "1234", "Admin");
        userList.add(users);

        when(userService.listAll()).thenReturn(userList);

        mvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(view().name("indexUser"))  // Assurez-vous que le nom de la vue correspond à celui renvoyé par votre contrôleur
                .andExpect(model().attribute("listUsers", userList)); // Notez que nous nous attendons maintenant à une liste d'utilisateurs

        // Vérifiez que userService.listAll() a été appelé une fois
        verify(userService, times(1)).listAll();
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    public void testShowNewUserForm() throws Exception {
        mvc.perform(get("/user/showNewUserForm"))
                .andExpect(status().isOk())
                .andExpect(view().name("newUser"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testSaveUser_Success() throws Exception {
        // Créer un UserForm pour le test
        UserForm userForm = new UserForm();
        userForm.setFullName("JohnDoe");
        userForm.setRole("ADMIN");
        userForm.setPassword("Password1@1112ha");
        userForm.setUsername("john");

        doNothing().when(userService).saveUser(any(Users.class));

        // Faire une requête POST à /saveUser et vérifier que le statut est une redirection
        mvc.perform(post("/user/saveUser")
                        .with(csrf())
                        .param("fullName", "John Doe")
                        .param("username", "john_doe")
                        .param("password", "Password1@")
                        .param("role", "ADMIN"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testSaveUser_Exception() throws Exception {
        // Créer un UserForm pour le test
        UserForm userForm = new UserForm();
        userForm.setFullName("JohnDoe");
        userForm.setRole("ADMIN");
        userForm.setPassword("Password1@1112ha");
        userForm.setUsername("john");

        // Configurer userService pour lancer une exception
        doThrow(new RuntimeException("Test exception")).when(userService).saveUser(any(Users.class));

        // Faire une requête POST à /saveUser et vérifier que le statut est 200 (OK)
        MvcResult result = mvc.perform(post("/user/saveUser")
                        .with(csrf())
                        .param("fullName", "John Doe")
                        .param("username", "john_doe")
                        .param("password", "Password1@")
                        .param("role", "ADMIN"))
                .andExpect(status().isOk())
                .andReturn();

        // Vérifier que le modèle contient l'attribut "errorMessage"
        assertTrue(result.getModelAndView().getModel().containsKey("errorMessage"));

        // Vérifier que le message d'erreur est correct
        assertEquals("An error occurred: Test exception", result.getModelAndView().getModel().get("errorMessage"));
    }



    @Test
    @WithMockUser(roles = "ADMIN")
    public void testShowFormForUpdate() throws Exception {
        when(userService.updateUser(anyLong())).thenReturn(new Users());

        mvc.perform(get("/user/showFormForUpdate/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("updateUser"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testShowFormForUpdate_Exception() throws Exception {
        // Configurer userService pour lancer une exception
        doThrow(new RuntimeException("Test exception")).when(userService).updateUser(anyLong());

        // Faire une requête GET à /showFormForUpdate/{id} et vérifier que le statut est une redirection
        MvcResult result = mvc.perform(get("/user/showFormForUpdate/1"))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        // Vérifier que la redirection est vers le bon URL
        assertEquals("redirect:/", result.getModelAndView().getViewName());

    }


    @Test
    @WithMockUser(roles = "ADMIN")
    public void testDeleteUser() throws Exception {
        doNothing().when(userService).delete(anyLong());

        mvc.perform(get("/user/deleteUser/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }
}


