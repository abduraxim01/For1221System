package com.practical.InternTask.controller;

import com.practical.InternTask.modelDTO.MealDTO;
import com.practical.InternTask.modelDTO.OrderDTO;
import com.practical.InternTask.service.MealService;
import com.practical.InternTask.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/meal")
public class MealController {

    private final MealService mealSer;

    private final UserService userSer;

    @Autowired
    public MealController(MealService mealSer, UserService userSer) {
        this.mealSer = mealSer;
        this.userSer = userSer;
    }

    @PostMapping(value = "/createMeal")
    public ResponseEntity<Void> createMeal(@RequestBody MealDTO dto) {
        try {
            mealSer.createMeal(dto);
            return new ResponseEntity<>(HttpStatusCode.valueOf(201));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(401));
        }
    }

    @PostMapping(value = "/eating")
    public ResponseEntity<Void> eating(@RequestBody OrderDTO dto) {
        try {
            mealSer.eating(dto);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
