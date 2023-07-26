package com.openclassrooms.poseidon.controllers;

import com.openclassrooms.poseidon.domain.Users;
import com.openclassrooms.poseidon.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
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
        // Créez une liste d'utilisateurs à renvoyer
        List<Users> userList = new ArrayList<>();
        Users users = new Users(1L, "Hamza", "ben", "1234", "Admin");
        userList.add(users);

        // Configurez userService pour renvoyer cette liste lorsque listAll() est appelé
        when(userService.listAll()).thenReturn(userList);

        // Effectuez une requête GET et vérifiez que le statut est OK et que la vue attendue est renvoyée
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

//    @Test
//    @WithMockUser(roles="ADMIN")
//    public void testSaveUser_Success() throws Exception {
//        when(userService.saveUser(any(Users.class))).thenReturn(new Users());
//
//        mvc.perform(post("/user/saveUser")
//                        .param("fullName", "John Doe")
//                        .param("username", "john_doe")
//                        .param("password", "password123")
//                        .param("role", "ADMIN"))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/"));
//    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testShowFormForUpdate() throws Exception {
        when(userService.get(anyLong())).thenReturn(new Users());

        mvc.perform(get("/user/showFormForUpdate/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("updateUser"));
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


