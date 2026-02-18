package com.ms.user.services;
import com.ms.user.models.UserModel;
import com.ms.user.producers.UserProducer;
import com.ms.user.repositories.UserRepository;
import javax.transaction.Transactional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserProducer  userProducer;

    public UserService(UserRepository userRepository, UserProducer userProducer) {
        this.userRepository = userRepository;
        this.userProducer = userProducer;
    }


    @Transactional
    public UserModel save(UserModel userModel){
       if (userRepository.existsByEmail(userModel.getEmail())) {
           throw new DataIntegrityViolationException("Email já existe: " + userModel.getEmail());
       }
       
       userModel = userRepository.save(userModel);
       try {
           userProducer.publishMessageEmail(userModel);
       } catch (Exception e) {
           System.out.println("Erro RabbitMQ: " + e.getMessage());
       }
       return userModel;
     }

    public List<UserModel> findAll(){
        return userRepository.findAll();
    }

}

