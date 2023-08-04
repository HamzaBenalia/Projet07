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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
                        .param("fullName", "JohnDoe")
                        .param("username", "doe")
                        .param("password", "Password1@1112ha")
                        .param("role", "ADMIN"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user"));
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
    public void showFormForUserUpdate_whenUserExists_returnsUpdateUserView() throws Exception {
        // Given
        Long id = 1L;
        Users existingUser = new Users();
        existingUser.setFullName("existingUser");
        when(userService.findById(id)).thenReturn(Optional.of(existingUser));

        // When / Then
        mvc.perform(get("/user/showFormForUpdate/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("updateUser"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void updateUser_whenUserFormValid_returnsRedirectUserView() throws Exception {
        // Given
        Long id = 1L;
        Users updatedUser = new Users();
        updatedUser.setFullName("updatedUser");
        when(userService.newUser(any(Long.class), any(Users.class))).thenReturn(updatedUser);

        // When / Then
        mvc.perform(post("/user/updateUser/{id}", id)
                        .with(csrf())
                        .param("fullName", "fifo")
                        .param("username", "updatedUser")
                        .param("password", "Password1@")
                        .param("role", "USER"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/user"));
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    public void testDeleteUser() throws Exception {
        doNothing().when(userService).delete(anyLong());

        mvc.perform(get("/user/deleteUser/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user"));
    }
}


