package com.practical.InternTask.modelDTO;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyEating {

    private UUID userId;

    private LocalDate day;
}
