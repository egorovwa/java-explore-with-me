package com.example.evmdtocontract.dto.compilation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewCompilationDto {
   private Collection<Long> events;
    @NonNull
    private Boolean pinned;
    @NotBlank
    private String title;
}
