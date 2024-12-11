package com.example.trackyourbills.services.impl;

import com.example.trackyourbills.dto.UserDTO;
import com.example.trackyourbills.entities.User;
import com.example.trackyourbills.exceptions.ResourceNotFoundException;
import com.example.trackyourbills.mappers.UserMapper;
import com.example.trackyourbills.repositories.UserRepository;
import com.example.trackyourbills.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDTO createUser(UserDTO userDTO) {
        User user=this.userRepository.save(UserMapper.toEntity(userDTO));
        return UserMapper.toDto(user);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, Long userID) {
        User user=this.userRepository
                .findById(userID)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with ID: " + userID));

        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        user.setPassword(userDTO.getPassword());

        User updatedUser = this.userRepository.save(user);

        return UserMapper.toDto(updatedUser);
    }

    @Override
    public void deleteUser(Long userID) {
        User user= this.userRepository.findById(userID).orElseThrow(() ->
                new ResourceNotFoundException("User not found with ID: " + userID));
        this.userRepository.delete(user);
    }

    @Override
    public UserDTO getUserById(Long userID) {
        User user= this.userRepository
                .findById(userID)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with ID: " + userID));
        return UserMapper.toDto(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users=this.userRepository.findAll();
        return users
                .stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

//    @Override
//    public UserDTO userLogin(String email, String password) {
//        return null;
//    }
}
