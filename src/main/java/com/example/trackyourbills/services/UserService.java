package com.example.trackyourbills.services;

import com.example.trackyourbills.dto.UserDTO;
import com.example.trackyourbills.entities.User;
import com.example.trackyourbills.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    //create
    UserDTO createUser(UserDTO userDTO);
    //update
    UserDTO updateUser(UserDTO userDTO, Long userID);
    //delete
    void deleteUser(Long userID);
    //get
    UserDTO getUserById(Long userID);
    //getAll
    List<UserDTO> getAllUsers();

//    //user login
//    UserDTO userLogin(String email, String password);
}
