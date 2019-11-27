package com.cramstack.cramstackAPIlearning.Controllers;


import com.cramstack.cramstackAPIlearning.Models.User;
import com.cramstack.cramstackAPIlearning.Payloads.UserPayload;
import com.cramstack.cramstackAPIlearning.Services.UserServices;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserServices userServices;

    @Autowired
    public UserController(UserServices userServices) {
        this.userServices = userServices;
    }

    @GetMapping(path = "/all")
    public List<User> AllUser(){

        return userServices.findAllUser();
    }


    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<String> userDeleted(@PathVariable("id") Integer id){

        Optional<User> existThisIdlUser = userServices.findUserById(id);

        if(existThisIdlUser.isPresent()){
            userServices.deleteUserById(existThisIdlUser.get());
            return new ResponseEntity<String>("User Deleted", HttpStatus.OK);
        }
        return null;
    }

    @GetMapping(path = "/{id}")
    public User findUser(@PathVariable("id") Integer id){

        Optional<User> existThisIdlUser = userServices.findUserById(id);

        return existThisIdlUser.orElse(null);
    }

    @PostMapping(path = "/create")
    public ResponseEntity<String> createUser(@RequestBody UserPayload userPayload , @Valid BindingResult bindingResult){

        User existThisEmailUser = userServices.findUserByEmail(userPayload.getEmail());

        if(existThisEmailUser != null){
            return new ResponseEntity<String>("Email is in use" ,HttpStatus.BAD_REQUEST);
        }

        if(bindingResult.hasErrors()){
            return new ResponseEntity<String>("Input have some error",HttpStatus.BAD_REQUEST);
        }

        if( userServices.createANewUser(userPayload) != null) {
            return new ResponseEntity<String>("user Created", HttpStatus.CREATED);
        }
        return null;
    }

    @PutMapping(path = "/edit/{id}")
    public ResponseEntity<String> editUser(@PathVariable("id") Integer id , @RequestBody UserPayload userPayload , @Valid BindingResult bindingResult){

        Optional<User> existThisIdlUser = userServices.findUserById(id);

        if(!existThisIdlUser.isPresent()){
            return new ResponseEntity<String>("There is no such user", HttpStatus.BAD_REQUEST);
        }

        if(bindingResult.hasErrors()){
            return new ResponseEntity<String>("Input have some error", HttpStatus.BAD_REQUEST);
        }

        if( userServices.updateUserProperty(id , userPayload) != null ){
            return new ResponseEntity<String>("user Updated", HttpStatus.OK);
        }
        return null;
    }

}
