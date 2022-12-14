package ru.practicum.ewmmainservice.models.location;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "locations")
@AllArgsConstructor
@NoArgsConstructor
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private Float lat;	/*number($float)
    example: 55.754167
    Широта*/
    @NotNull
    private Float lon;	/*number($float)
    example: 37.62
    Долгота*/


}
