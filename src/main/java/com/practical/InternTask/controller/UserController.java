package com.practical.InternTask.controller;

import com.practical.InternTask.modelDTO.DailyEating;
import com.practical.InternTask.modelDTO.MealDTO;
import com.practical.InternTask.modelDTO.ResultDTO;
import com.practical.InternTask.modelDTO.UserDTO;
import com.practical.InternTask.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    private final UserService userSer;

    @Autowired
    public UserController(UserService userSer) {
        this.userSer = userSer;
    }

    @PostMapping(value = "/createUser")
    @Operation(description = "Api is used to register new users")
    public ResponseEntity<Void> createUser(@RequestBody UserDTO dto) {
        try {
            userSer.createUser(dto);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(description = "Api is used to get food history")
    @GetMapping(value = "/getDailyEating")
    public ResponseEntity<List<MealDTO>> getDailyEating(@RequestBody DailyEating dailyEating) {
        try {
            return ResponseEntity.ok(userSer.getDailyEating(dailyEating));
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/getDailyEatingWithCalories")
    @Operation(description = "Api is used to get food history with its calorie")
    public ResponseEntity<Map<String, Float>> getDailyEatingWithCalories(@RequestBody DailyEating history) {
        try {
            return ResponseEntity.ok(userSer.getDailyEatingWithCalories(history));
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/getCheckDailyCalories/{userId}")
    @Operation(description = "Api is used to get daily result")
    public ResponseEntity<ResultDTO> getCheckDailyCalorie(@PathVariable UUID userId) {
        try {
            return ResponseEntity.ok(userSer.getCheckDailyCalorie(userId));
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
