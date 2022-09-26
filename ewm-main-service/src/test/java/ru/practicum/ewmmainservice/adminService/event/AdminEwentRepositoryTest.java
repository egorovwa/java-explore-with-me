package ru.practicum.ewmmainservice.adminService.event;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
import ru.practicum.ewmmainservice.adminService.category.CategoryRepository;
import ru.practicum.ewmmainservice.adminService.user.UserAdminRepository;
import ru.practicum.ewmmainservice.exceptions.IncorrectPageValueException;
import ru.practicum.ewmmainservice.models.category.Category;
import ru.practicum.ewmmainservice.models.event.Event;
import ru.practicum.ewmmainservice.models.event.EventState;
import ru.practicum.ewmmainservice.models.location.Location;
import ru.practicum.ewmmainservice.models.participationRequest.ParticipationRequest;
import ru.practicum.ewmmainservice.models.user.User;
import ru.practicum.ewmmainservice.privateservise.event.PrivateEventRepository;
import ru.practicum.ewmmainservice.privateservise.location.LocationRepository;
import ru.practicum.ewmmainservice.utils.PageParam;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.practicum.ewmmainservice.models.event.EventState.PUBLISHED;
import static ru.practicum.ewmmainservice.models.event.EventState.WAITING;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@DataJpaTest
class AdminEwentRepositoryTest {
    @Autowired
    AdminEwentRepository adminEwentRepository;
    @Autowired
    UserAdminRepository userAdminRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    PrivateEventRepository privateEventRepository;
    @Autowired
    LocationRepository locationRepository;
    Event event1;
    Event event2;
    Event event3;


    @Test
    void test1_1findForAdmin() throws IncorrectPageValueException {
        List<Long> userIds = List.of(1L, 2L);
        List<Long> catIds = List.of(1L, 2L);
        List<EventState> states = List.of(PUBLISHED, WAITING);
        Long start = LocalDateTime.of(2022, 9, 5, 1, 0, 0).toEpochSecond(ZoneOffset.UTC);
        Long end = LocalDateTime.of(2022, 9, 11, 1, 0, 0).toEpochSecond(ZoneOffset.UTC);
        Pageable pageable = PageParam.createPageable(0, 10);
        data();
        List<Event> expected = new ArrayList<>();
        expected.add(event1);
        expected.add(event2);
        List<Event> result = adminEwentRepository.findForAdmin(userIds, catIds, states, start, end);
        Event rea = expected.get(0);
        Event res = result.get(0);
        Boolean a = res.equals(rea);
        assertEquals(res, rea);


    }

    private void data() {
        Category category1 = new Category(1L, "category1");
        Category category2 = new Category(2L, "category2");
        Category category3 = new Category(3L, "category3");
        User user1 = new User(1L, "email@mail.ru", "name");
        Location location = new Location(1L, 1.0f, 2.0f);
        User user2 = new User(2L, "emai@rrr.ru", "name2");
        User user3 = new User(3L, "sss@sss.fff", "name3");
        List<User> participans = List.of(user2, user3);
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
                "title",
                5,
                participans);
        event2 = new Event(2L,
                "anatationanatationanatationanatationanatationanatationanatation",
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
                WAITING,
                "title",
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
                EventState.CANCELLED,
                "title",
                5,
                participans);
        userAdminRepository.save(user1);
        userAdminRepository.save(user2);
        userAdminRepository.save(user3);
        categoryRepository.save(category1);
        categoryRepository.save(category2);
        categoryRepository.save(category3);
        locationRepository.save(location);
        adminEwentRepository.save(event1);
        adminEwentRepository.save(event2);
        adminEwentRepository.save(event3);

    }
}