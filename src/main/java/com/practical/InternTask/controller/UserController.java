package com.practical.InternTask.controller;

import com.practical.InternTask.modelDTO.DailyEating;
import com.practical.InternTask.modelDTO.UserDTO;
import com.practical.InternTask.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    private final UserService userSer;

    public UserController(UserService userSer) {
        this.userSer = userSer;
    }

    @PostMapping(value = "/createUser")
    public ResponseEntity<Void> createUser(@RequestBody UserDTO dto) {
        try {
            userSer.createUser(dto);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/getDailyEating")
    public ResponseEntity<List<?>> getDailyEating(@RequestBody DailyEating history) {
        try {
            return ResponseEntity.ok(userSer.getDailyEating(history));
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/getDailyEatingWithCalories")
    public ResponseEntity<?> getDailyEatingWithCalories(@RequestBody DailyEating history) {
        try {
            return ResponseEntity.ok(userSer.getDailyEatingWithCalories(history));
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/getCheckDailyCalories/{userId}")
    public ResponseEntity<?> getCheckDailyCalorie(@PathVariable UUID userId) {
        try {
            return ResponseEntity.ok(userSer.getCheckDailyCalorie(userId));
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
