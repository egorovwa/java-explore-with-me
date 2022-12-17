package com.example.evmdtocontract.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
   private Long id;
    @Email
    private String email;
    @Size(min = 3)
    private String name;
}
