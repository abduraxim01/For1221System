package com.practical.InternTask.controller;

import com.practical.InternTask.modelDTO.MealDTO;
import com.practical.InternTask.modelDTO.OrderDTO;
import com.practical.InternTask.service.MealService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Autowired
    public MealController(MealService mealSer) {
        this.mealSer = mealSer;
    }

    @PostMapping(value = "/createMeal")
    @Operation(description = "Api is used to create new food")
    public ResponseEntity<Void> createMeal(@RequestBody MealDTO dto) {
        try {
            mealSer.createMeal(dto);
            return new ResponseEntity<>(HttpStatusCode.valueOf(201));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(401));
        }
    }

    @PostMapping(value = "/eating")
    @Operation(description = "Api is used to add food to orders")
    public ResponseEntity<Void> eating(@RequestBody OrderDTO dto) {
        try {
            mealSer.eating(dto);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
