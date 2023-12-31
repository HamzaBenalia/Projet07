package com.openclassrooms.poseidon.services;

import com.openclassrooms.poseidon.domain.Users;

import java.util.List;
import java.util.Optional;

public interface UserService {


    void saveUser(Users user);

    Optional<Users> findUserByUsername(String username);


    List<Users> listAll();

    Optional<Users> findById(Long id);


    Users updateUser(Long id);

    Users newUser(Long id, Users updatedUser);


    void delete(Long id);
}
