package com.ms.user.services;

import com.ms.user.exceptions.EmailAlreadyInUseException;
import com.ms.user.models.UserModel;
import com.ms.user.producers.UserProducer;
import com.ms.user.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    final UserRepository userRepository;
    final UserProducer userProducer;

    public UserService(UserRepository userRepository, UserProducer userProducer) {
        this.userRepository = userRepository;
        this.userProducer = userProducer;
    }

    @Transactional
    public UserModel save(UserModel userModel) {
        if (userRepository.findByEmail(userModel.getEmail()).isPresent()) {
            throw new EmailAlreadyInUseException("Email already in use");
        }
        userModel = userRepository.save(userModel);
        userProducer.publishMessageEmail(userModel);
        return userModel;
    }

    @Transactional
    public UserModel getUserById(UUID userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Transactional
    public List<UserModel> getAllUsers() {
        return userRepository.findAll();
    }
    @Transactional
    public void deleteUserById(UUID userId) {
        userRepository.deleteById(userId);
    }
}