package ru.practicum.ewmmainservice.publicservice.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.practicum.ewmmainservice.adminservice.category.CategoryRepository;
import ru.practicum.ewmmainservice.adminservice.event.AdminEwentRepository;
import ru.practicum.ewmmainservice.adminservice.event.impl.AdminEventServiceImpl;
import ru.practicum.ewmmainservice.adminservice.user.UserAdminRepository;
import ru.practicum.ewmmainservice.models.category.Category;
import ru.practicum.ewmmainservice.models.event.Event;
import ru.practicum.ewmmainservice.models.event.EventState;
import ru.practicum.ewmmainservice.models.event.dto.EventDtoMaper;
import ru.practicum.ewmmainservice.models.location.Location;
import ru.practicum.ewmmainservice.models.location.dto.LocationFullDto;
import ru.practicum.ewmmainservice.models.participationrequest.ParticipationRequest;
import ru.practicum.ewmmainservice.models.user.User;
import ru.practicum.ewmmainservice.privateservise.location.LocationRepository;
import ru.practicum.ewmstatscontract.utils.Utils;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.practicum.ewmmainservice.models.event.EventState.PENDING;
import static ru.practicum.ewmmainservice.models.event.EventState.PUBLISHED;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@AutoConfigureTestDatabase
class PublicEventControllerTest {
    private static final String API = "/events";
    @Autowired
    PublicEventService publicEventService;
    @Autowired
    UserAdminRepository userAdminRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    LocationRepository locationRepository;
    @Autowired
    AdminEwentRepository adminEwentRepository;
    @Autowired
    AdminEventServiceImpl adminEventService;
    @Autowired
    EventDtoMaper eventDtoMaper;
    DateTimeFormatter formatter = Utils.getDateTimeFormatter();
    Event event1;
    Event event2;
    Event event3;
    @Autowired
    ObjectMapper mapper;
    MockMvc mvc;

    @BeforeEach
    void setup(WebApplicationContext web) {
        mvc = MockMvcBuilders.webAppContextSetup(web)
                .build();
    }

    @Test
    @DirtiesContext
    void test1_findEvents() throws Exception {
        String[] categories = {"1"};
        Long[] catToParam = {1L};
        data();

        mvc.perform(get(API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .param("text", "event")
                        .param("categories", "2")
                        .param("paid", String.valueOf(true))
                        .param("rangeStart", "2022-09-08 00:00:00")
                        .param("rangeEnd", "2022-09-10 00:00:00")
                        .param("onlyAvailable", String.valueOf(true))
                        .param("sort", "VIEWS")
                        .param("from", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)))
                .andExpect(jsonPath("$[0].title", is("event2")));
    }

    @Test
    @DirtiesContext
    void findById() throws Exception {
        data();
        mvc.perform(get(API + "/{id}", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("event1")));
    }

    private void data() {
        Category category1 = new Category(1L, "category1");
        Category category2 = new Category(2L, "category2");
        Category category3 = new Category(3L, "category3");
        User user1 = new User(1L, "email@mail.ru", "name");
        Location location = new Location(1L, "location", 83.1454, 53.4545, 5000, null, new ArrayList<>(), true);
        User user2 = new User(2L, "emai@rrr.ru", "name2");
        User user3 = new User(3L, "sss@sss.fff", "name3");
        User user4 = new User(4L, "sss@sssl4.fff", "name4");
        Collection<User> participans = new ArrayList<>();
        participans.add(user4);

        ParticipationRequest participationRequest = new ParticipationRequest();
        event1 = new Event(1L,
                "anatationanatationanatationanatationanatation",
                category1,
                LocalDateTime.of(2022, 9, 6, 11, 0, 23).toEpochSecond(ZoneOffset.UTC), //2022-09-06 11:00:23
                "DescriptionDescriptionDescriptionDescriptionDescriptionDescription",
                LocalDateTime.of(2022, 9, 7, 11, 0, 23).toEpochSecond(ZoneOffset.UTC), //eventDate
                user1,
                location,
                true,
                10,
                LocalDateTime.of(2022, 9, 11, 16, 11, 0).toEpochSecond(ZoneOffset.UTC),
                true,
                PUBLISHED,
                "event1",
                5,
                new ArrayList<>());
        event2 = new Event(2L,
                "event event event event",
                category2,
                LocalDateTime.of(2022, 9, 6, 11, 0, 23).toEpochSecond(ZoneOffset.UTC), //2022-09-06 11:00:23
                "DescriptionDescriptionDescriptionDescriptionDescriptionDescription",
                LocalDateTime.of(2022, 9, 8, 11, 0, 23).toEpochSecond(ZoneOffset.UTC), //eventDate
                user2,
                location,
                true,
                10,
                LocalDateTime.of(2022, 9, 11, 16, 11, 0).toEpochSecond(ZoneOffset.UTC),
                true,
                PENDING,
                "event2",
                5,
                participans);
        event3 = new Event(3L,
                "anatation anatation anatation anatation",
                category3,
                LocalDateTime.of(2022, 9, 6, 11, 0, 23).toEpochSecond(ZoneOffset.UTC), //2022-09-06 11:00:23
                "Description DescriptionDescriptionDescriptionDescriptionDescriptionDescription",
                LocalDateTime.of(2022, 9, 10, 11, 0, 23).toEpochSecond(ZoneOffset.UTC), //eventDate
                user3,
                location,
                true,
                10,
                LocalDateTime.of(2022, 9, 11, 16, 11, 0).toEpochSecond(ZoneOffset.UTC),
                true,
                EventState.CANCELED,
                "title",
                5,
                new ArrayList<>());
        userAdminRepository.save(user1);
        userAdminRepository.save(user2);
        userAdminRepository.save(user3);
        userAdminRepository.save(user4);
        categoryRepository.save(category1);
        categoryRepository.save(category2);
        categoryRepository.save(category3);
        locationRepository.save(location);
        adminEwentRepository.save(event1);
        adminEwentRepository.save(event2);
        adminEwentRepository.save(event3);

    }
}