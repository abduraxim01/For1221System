package com.practical.InternTask.modelDTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    private String name;

    private String email;

    private int age;

    private Float height;

    private Float weight;

    private String purpose;

    private String gender;

}
