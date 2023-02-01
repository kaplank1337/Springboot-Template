package com.example.udemyspring;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/createUser")
    public ResponseEntity<User> login (@RequestBody User user){
        //generate secret
        user.setSecret(UUID.randomUUID().toString());

       var savedUser = userRepository.save(user);
        return new ResponseEntity<User>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping("/user")
    public ResponseEntity<User> getUser(@RequestParam(value="id") Long id){
        Optional<User> userByID= userRepository.findById(id);
        return new ResponseEntity<User>(userByID.get(),HttpStatus.OK);
    }

    @GetMapping("/validate")
    public ResponseEntity<String> login(@RequestParam(value="email")String email,
                                      @RequestParam(value="password")String password){

        Optional<User> validUser = userRepository.findByEmailAndPassword(email,password);

        if(validUser.isPresent()){
            return new ResponseEntity<String>("API Secret " + validUser.get().getSecret(), HttpStatus.OK);
        }
        return new ResponseEntity<String>("Nichts gefunden", HttpStatus.NOT_FOUND);
    }
}
