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
}
