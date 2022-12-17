package com.example.evmdtocontract.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewUserDto {
    @Email
    @NotNull
    private String email;
    @NotBlank
    private String name;
}
