package com.example.evmdtocontract.dto.event;

import com.example.evmdtocontract.dto.category.CategoryDto;
import com.example.evmdtocontract.dto.user.UserShortDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventShortDto {
    private Long id;
    @NotNull
    @Size(max = 2000, min = 20)
    private String annotation;
    @NotNull
    private CategoryDto category;
    @NotNull
    private String eventDate;
    private int confirmedRequests;
    @NotNull
    private UserShortDto initiator;
    @NotNull
    private Boolean paid;
    @NotBlank
    @Size(min = 3, max = 120)
    private String title;
    private int views;
}
