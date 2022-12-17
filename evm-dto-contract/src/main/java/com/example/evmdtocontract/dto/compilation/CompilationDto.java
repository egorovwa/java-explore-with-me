package com.example.evmdtocontract.dto.compilation;

import com.example.evmdtocontract.dto.event.EventShortDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompilationDto {
    private Collection<EventShortDto> events;
    private Long id;
    @NonNull
    private Boolean pinned;
    @NonNull
    @NotBlank
    private String title;
}
