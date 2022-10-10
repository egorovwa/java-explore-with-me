package ru.practicum.ewmmainservice.adminservice.location.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.ewmmainservice.adminservice.location.AdminLocationRepository;
import ru.practicum.ewmmainservice.adminservice.location.AdminLocationService;
import ru.practicum.ewmmainservice.exceptions.LocationException;
import ru.practicum.ewmmainservice.exceptions.ModelAlreadyExistsException;
import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.models.location.Location;
import ru.practicum.ewmmainservice.models.location.dto.LocationFullDto;
import ru.practicum.ewmmainservice.models.location.dto.NewLocationDto;

import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class AdminLocationServiceImplTest {
   private final AdminLocationService service;
   private final AdminLocationRepository repository;
   private final ObjectMapper objectMapper;

    @Test
    void test1_createLocation_findChilds() throws NotFoundException, LocationException, JsonProcessingException, ModelAlreadyExistsException {
        NewLocationDto newParent = new NewLocationDto("parent", 53.404809, 83.807314, 500, null);
        NewLocationDto loc1 = new NewLocationDto("loc1", 53.404809, 83.807314, 10, null);
        NewLocationDto loc2 = new NewLocationDto("loc2", 53.404672, 83.812082, 10, null);
        NewLocationDto loc3 = new NewLocationDto("loc3", 53.395185, 83.801458, 10, null);
        NewLocationDto loc4 = new NewLocationDto("loc4", 53.405812, 83.809389, 10, null);
        Location res1 = service.createLocation(loc1);
        Location res2 = service.createLocation(loc2);
        Location res3 = service.createLocation(loc3);
        Location res4 = service.createLocation(loc4);
        Location result  = service.createLocation(newParent);
        System.out.println(result);
        System.out.println(objectMapper.writeValueAsString(newParent));

    }
}