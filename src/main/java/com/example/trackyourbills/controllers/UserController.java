package com.example.trackyourbills.controllers;

import com.example.trackyourbills.dto.UserDTO;
import com.example.trackyourbills.security.user.ChangePasswordRequest;
import com.example.trackyourbills.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        UserDTO user = this.userService.createUser(userDTO);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/{userID}")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO, @PathVariable Long userID){
        UserDTO user = this.userService.updateUser(userDTO, userID);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/{userID}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long userID){
        UserDTO userDTO =  this.userService.getUserById(userID);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        List<UserDTO> userDTOs =  this.userService.getAllUsers();
        return ResponseEntity.ok(userDTOs);
    }

    @DeleteMapping("/{userID}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userID){
        this.userService.deleteUser(userID);
        return ResponseEntity.ok("User deleted successfully");
    }

    @PatchMapping
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal connectedUser
    ) {
        userService.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }
}
