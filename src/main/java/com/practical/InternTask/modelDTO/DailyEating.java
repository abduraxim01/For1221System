package com.practical.InternTask.modelDTO;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyEating {

    private String userId;

    private LocalDate day;
}
