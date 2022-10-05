package ru.practicum.ewmmainservice.utils;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.practicum.ewmmainservice.exceptions.IncorrectPageValueException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PageParamTest {

    @Test
    void test1_createPageable_withSort() throws IncorrectPageValueException {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("column").descending());
        Pageable result = PageParam.createPageable(0, 10, "column");
        assertThat(result, is(pageable));
    }

    @Test
    void test2_createPageable_whenFromNull() throws IncorrectPageValueException {

        Pageable result = PageParam.createPageable(null, 10, "column");
        assertNull(result);
    }

    @Test
    void test3_createPageable_whenSiziNull() throws IncorrectPageValueException {

        Pageable result = PageParam.createPageable(1, null, "column");
        assertNull(result);
    }

    @Test
    void test4_createPageable_whenFromNegative() {
        assertThrows(IncorrectPageValueException.class, () -> {
            Pageable result = PageParam.createPageable(-1, 10, "column");
        });
    }

    @Test
    void test4_createPageable_whenSizeNegative() {
        assertThrows(IncorrectPageValueException.class, () -> {
            Pageable result = PageParam.createPageable(1, 0, "column");
        });
    }

    @Test
    void test5_createPageable_withOutSort() throws IncorrectPageValueException {
        Pageable pageable = PageRequest.of(0, 10);
        Pageable result = PageParam.createPageable(0, 10);
        assertThat(result, is(pageable));
    }

    @Test
    void test6_createPageable_whenFromNull() throws IncorrectPageValueException {

        Pageable result = PageParam.createPageable(null, 10);
        assertNull(result);
    }

    @Test
    void test7_createPageable_whenSiziNull() throws IncorrectPageValueException {

        Pageable result = PageParam.createPageable(1, null);
        assertNull(result);
    }

    @Test
    void test8_createPageable_whenFromNegative() {
        assertThrows(IncorrectPageValueException.class, () -> {
            Pageable result = PageParam.createPageable(-1, 10);
        });
    }

    @Test
    void test9_createPageable_whenSizeNegative() {
        assertThrows(IncorrectPageValueException.class, () -> {
            Pageable result = PageParam.createPageable(1, 0);
        });
    }
}