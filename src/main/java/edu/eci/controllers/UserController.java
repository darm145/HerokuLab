package edu.eci.controllers;

import edu.eci.models.UniqueId;
import edu.eci.models.User;
import edu.eci.services.UserServices;
import edu.eci.services.contracts.IUserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserServices userServices;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getUser(){
        try{
            return new ResponseEntity<>(userServices.list(), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getStackTrace(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> createUser(@RequestBody User user){
        try{
        	
            return new ResponseEntity<>(userServices.save(user), HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> updateUser(@RequestBody User user){
    	try{
    		userServices.update(user);
            return new ResponseEntity<>( HttpStatus.OK); 
        }catch(Exception e){
            return new ResponseEntity<>(e.getStackTrace(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.DELETE, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> deleteUser(@RequestBody UniqueId temp){
    	try{
    		userServices.remove(temp.getId());
    		
            return new ResponseEntity<>( HttpStatus.OK); 
        }catch(Exception e){
            return new ResponseEntity<>(e.getStackTrace(), HttpStatus.FORBIDDEN);
        }
    }
}
