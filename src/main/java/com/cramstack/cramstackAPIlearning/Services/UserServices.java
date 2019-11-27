package com.cramstack.cramstackAPIlearning.Services;


import com.cramstack.cramstackAPIlearning.Models.User;
import com.cramstack.cramstackAPIlearning.Payloads.UserPayload;
import com.cramstack.cramstackAPIlearning.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServices {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServices(UserRepository userRepository , BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Optional<User> findUserById(Integer id) {
        return userRepository.findById(id);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User createANewUser(UserPayload userPayload) {

        User user = new User();
        return saveUser(userPayload, user);
    }

    public User updateUserProperty(Integer id, UserPayload userPayload) {

        User user = new User();
        user.setId(id);
        return saveUser(userPayload, user);
    }

    private User saveUser(UserPayload userPayload, User user) {
        user.setName(userPayload.getName());
        user.setEmail(userPayload.getEmail());
        user.setDesignation(userPayload.getDesignation());
        user.setEnabled(userPayload.isEnabled());
        user.setPassword(bCryptPasswordEncoder.encode(userPayload.getPassword()));

        return userRepository.save(user);
    }

    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    public void deleteUserById(User user) {
        userRepository.delete(user);
    }
}
