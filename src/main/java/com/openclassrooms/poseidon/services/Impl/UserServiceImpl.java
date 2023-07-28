package com.openclassrooms.poseidon.services.Impl;

import com.openclassrooms.poseidon.domain.Users;
import com.openclassrooms.poseidon.repositories.UserRepository;
import com.openclassrooms.poseidon.services.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;


    public void saveUser(Users user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public Optional<Users> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    public List<Users> listAll() {
        return userRepository.findAll();
    }


    public Users updateUser(Long id) {
        return userRepository.findById(id).get();
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
