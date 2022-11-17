package ru.practicum.ewmmainservice.models.parameters;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;
import ru.practicum.ewmmainservice.exceptions.IllegalTimeException;
import ru.practicum.ewmmainservice.exceptions.IncorrectPageValueException;
import ru.practicum.ewmmainservice.utils.PageParam;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParametersPublicEventFindTest {
    @Test
    void test_1createParametersPublicEventFind_whenillegalTime() {
        Long[] catIds = {1L};
        assertThrows(IllegalTimeException.class, () -> new ParametersPublicEventFind("text", catIds, true,
                "2020-02-02 00:00:00", "2020-01-01 00:00:00", true, "VIEWS", 0,
                10, "ip", "/event"));
    }

    @Test
    void test2_createParametersPublicEventFind_whenIllegalSort() {
        Long[] catIds = {1L};
        assertThrows(IllegalArgumentException.class, () -> {
            new ParametersPublicEventFind("text", catIds, true, "2020-02-02 00:00:00",
                    "2020-03-01 00:00:00", true, "aaaaa", 0, 10, "ip", "/event");
        });
    }

    @Test
    void test3_createParametersPublicEventFind_whenSortView() throws IncorrectPageValueException, IllegalTimeException {
        Long[] catIds = {1L};
        ParametersPublicEventFind param = new ParametersPublicEventFind("text", catIds, true, "2020-02-02 00:00:00",
                "2020-03-01 00:00:00", true, "VIEWS", 0, 10, "ip", "/event");
        Pageable pageable = PageParam.createPageable(0, 10, "views");
        assertThat(param.getPageable(), is(pageable));
    }

    @Test
    void test4_createParametersPublicEventFind_whenSortEVENT_DATE() throws IncorrectPageValueException, IllegalTimeException {
        Long[] catIds = {1L};
        ParametersPublicEventFind param = new ParametersPublicEventFind("text", catIds, true, "2020-02-02 00:00:00",
                "2020-03-01 00:00:00", true, "EVENT_DATE", 0, 10, "ip", "/event");
        Pageable pageable = PageParam.createPageable(0, 10, "eventDate");
        assertThat(param.getPageable(), is(pageable));
    }
}