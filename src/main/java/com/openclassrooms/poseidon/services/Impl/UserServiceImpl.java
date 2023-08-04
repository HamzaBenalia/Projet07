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

    @Override
    public Optional<Users> findById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<Users> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    public List<Users> listAll() {
        return userRepository.findAll();
    }

    @Override
    public Users newUser(Long id, Users updatedUser) {
        updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));

        Optional<Users> existingUserOpt = userRepository.findById(id);
        if (existingUserOpt.isPresent()) {
            Users existingUser = existingUserOpt.get();
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setFullName(updatedUser.getFullName());
            existingUser.setRole(updatedUser.getRole());
            existingUser.setPassword(updatedUser.getPassword());

            return userRepository.save(existingUser);
        } else {
            throw new RuntimeException("BidList not found with id " + id);
        }
    }


    public Users updateUser(Long id) {
        return userRepository.findById(id).get();
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
