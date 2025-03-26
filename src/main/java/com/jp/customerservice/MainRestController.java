package com.jp.customerservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class MainRestController {


    @Autowired
    CredentialRepository credentialRepo;

    @Autowired
    TokenService tokenService;

    private static final Logger log = LoggerFactory.getLogger(MainRestController.class);

    @GetMapping("/test")
    public ResponseEntity<?> testProductService() {
        return ResponseEntity.ok("Customer Service running fine");
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Credential newCredential) {

        log.info("Signup endpoint called with data {} ",newCredential.toString());
        newCredential.setRole("USER");
        credentialRepo.save(newCredential);
        log.info("Credentials stored successfully in the db.");
        return ResponseEntity.ok("Signup successful.");
    }

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestBody Credential credential) throws JsonProcessingException {
        log.info("Received request to login: {}", credential);
        if(credentialRepo.findById(credential.getUsername()).isPresent())
        {
            log.info("Credential exists: {}", credential);
            Credential fetchedCredential = credentialRepo.findById(credential.getUsername()).get();
            log.info("Fetched credential: {}", fetchedCredential);
            if(credential.getPassword().equals(fetchedCredential.getPassword()))
            {
                log.info("Login Successful: {}", credential);
                Token token =  tokenService.generateToken(credential.getUsername());
                log.info("Token generated: {}", token);
               // producer.publishAuthDatum(credential.getUsername(), "LOGIN");
                String tokenToReturn = credential.getUsername()+":"+token.getTokenId();
                return ResponseEntity.ok().header("Authorization",tokenToReturn).body("Login Successful");
            }
            else
            {
                //producer.publishAuthDatum(credential.getUsername(), "LOGIN_FAILED | INCORRECT_PASSWORD");
                log.info("Login Failed due to incorrect password: {}", credential);
                return ResponseEntity.badRequest().build();
            }
        }
        else {
           // producer.publishAuthDatum(credential.getUsername(), "LOGIN_FAILED | USER_NOT_FOUND");
            log.info("Credential does not exist: {}", credential);
            return ResponseEntity.ok("Credential does not exist");
        }
    }

    @GetMapping("validate")
    public ResponseEntity<?> validate(@RequestHeader("Authorization") String token) throws JsonProcessingException {
        log.info("Received request to validate token: {}", token);
        if(tokenService.validateToken(token))
        {
            log.info("Token is valid: {}", token);
           // producer.publishAuthDatum(tokenService.getUsername(token), "TOKEN VALIDATED");
            return ResponseEntity.ok("valid");
        }
        else
        {
            log.info("Token is invalid: {}", token);
            return ResponseEntity.status(401).build();
        }
    }

    @GetMapping("logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) throws JsonProcessingException {

        tokenService.invalidateToken(token);
        //producer.publishAuthDatum(tokenService.getUsername(token), "LOGOUT");
        return ResponseEntity.ok("Logged out successfully");
    }




}
