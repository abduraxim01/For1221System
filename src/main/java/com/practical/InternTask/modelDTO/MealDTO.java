package com.practical.InternTask.modelDTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MealDTO {

    private String name;

    private float calorie;

    private float protein;

    private float carbs;

    private float fat;

    @Override
    public String toString() {
        return "Name= " + name  +
                ", calorie=" + calorie +
                ", protein=" + protein +
                ", carbs=" + carbs +
                ", fat=" + fat;
    }
}
