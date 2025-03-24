package com.practical.InternTask.modelDTO;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {

    private UUID userId;

    private UUID mealId;
}
