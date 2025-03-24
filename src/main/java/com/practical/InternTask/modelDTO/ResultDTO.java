package com.practical.InternTask.modelDTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultDTO {

    private float dailyCalorie;

    private float recommendedCalorie;

    private String content;
}
