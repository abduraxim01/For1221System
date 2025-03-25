package com.practical.InternTask.mapper;

import com.practical.InternTask.model.Meal;
import com.practical.InternTask.modelDTO.MealDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class MealMapper {

    public Meal toModel(MealDTO dto) {
        return Meal.builder()
                .name(dto.getName())
                .fat(dto.getFat())
                .carbs(dto.getCarbs())
                .calorie(dto.getCalorie())
                .protein(dto.getProtein())
                .build();
    }

    public MealDTO toDTO(Meal meal) {
        return MealDTO.builder()
                .name(meal.getName())
                .calorie(meal.getCalorie())
                .carbs(meal.getCarbs())
                .fat(meal.getFat())
                .protein(meal.getProtein())
                .build();
    }

    public List<MealDTO> toDTO(List<Meal> meals) {
        if (meals == null || meals.isEmpty()) return new ArrayList<>();
        System.out.println(Arrays.toString(meals.toArray()));
        return meals.stream()
                .map(this::toDTO)
                .toList();
    }
}
