package com.practical.InternTask.mapper;

import com.practical.InternTask.model.Gender;
import com.practical.InternTask.model.Purpose;
import com.practical.InternTask.model.User;
import com.practical.InternTask.modelDTO.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toModel(UserDTO dto) {
        return User.builder()
                .name(dto.getName())
                .purpose(Purpose.valueOf(dto.getPurpose()))
                .age(dto.getAge())
                .email(dto.getEmail())
                .gender(Gender.valueOf(dto.getName()))
                .height(dto.getHeight())
                .weight(dto.getWeight())
                .build();
    }
}
