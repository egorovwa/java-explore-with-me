package ru.practicum.ewmmainservice.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointsOperationsTest {

    @Test
    void getDistance() {
        System.out.println(PointsOperations.getDistance(56.291965, 39.333149, 56.298182, 39.333160));
    }
}