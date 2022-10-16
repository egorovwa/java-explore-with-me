package ru.practicum.ewmmainservice.adminservice.event.impl;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.ewmmainservice.adminservice.category.CategoryRepository;
import ru.practicum.ewmmainservice.adminservice.event.AdminEwentRepository;
import ru.practicum.ewmmainservice.adminservice.user.UserAdminRepository;
import ru.practicum.ewmmainservice.exceptions.IllegalTimeException;
import ru.practicum.ewmmainservice.exceptions.IncorrectPageValueException;
import ru.practicum.ewmmainservice.exceptions.NotValidParameterException;
import ru.practicum.ewmmainservice.models.category.Category;
import ru.practicum.ewmmainservice.models.event.Event;
import ru.practicum.ewmmainservice.models.event.EventState;
import ru.practicum.ewmmainservice.models.event.dto.EventDtoMaper;
import ru.practicum.ewmmainservice.models.event.dto.EventFullDto;
import ru.practicum.ewmmainservice.models.location.Location;
import ru.practicum.ewmmainservice.models.location.dto.LocationFullDto;
import ru.practicum.ewmmainservice.models.parameters.ParametersAdminFindEvent;
import ru.practicum.ewmmainservice.models.participationrequest.ParticipationRequest;
import ru.practicum.ewmmainservice.models.user.User;
import ru.practicum.ewmmainservice.privateservise.location.LocationRepository;
import ru.practicum.ewmmainservice.utils.PageParam;
import ru.practicum.ewmstatscontract.utils.Utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.practicum.ewmmainservice.models.event.EventState.PENDING;
import static ru.practicum.ewmmainservice.models.event.EventState.PUBLISHED;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class AdminEventServiceImplTestIntegration {
    @Autowired
    UserAdminRepository userAdminRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    LocationRepository locationRepository;
    @Autowired
    AdminEwentRepository adminEwentRepository;
    @Autowired
    AdminEventServiceImpl service;
    @Autowired
    EventDtoMaper eventDtoMaper;
    final DateTimeFormatter formatter = Utils.getDateTimeFormatter();
    Event event1;
    Event event2;
    Event event3;


    @Test
    @DirtiesContext
    void test1_1findAllEvents() throws IncorrectPageValueException, NotValidParameterException, IllegalTimeException {
        data();
        Long[] usersId = {1L, 2L};
        Long[] catId = {1L, 2L};
        Long[] locIds = {1L};
        String[] states = {"PENDING", "PUBLISHED"};
        String start = formatter.format(LocalDateTime.of(2022, 9, 5, 0, 0, 0));
        String end = formatter.format(LocalDateTime.of(2022, 9, 12, 0, 0, 0));
        Pageable pageable = PageParam.createPageable(0, 10);
        ParametersAdminFindEvent param = new ParametersAdminFindEvent(usersId, states, catId, locIds, start, end, 0, 10);
        List<EventFullDto> expected = List.of(eventDtoMaper.toFulDto(event1), eventDtoMaper.toFulDto(event2));
        List<EventFullDto> result = service.findAllEvents(param);
        assertThat(expected.get(0), is(expected.get(0)));
    }

    @Test
    @DirtiesContext
    void test1_2findAllEvents_whenCategoryEmpty_stateWaiting() throws IncorrectPageValueException, NotValidParameterException, IllegalTimeException {
        data();
        Long[] usersId = {1L, 2L};
        Long[] catId = {};
        Long[] locIds = {1L};
        String[] states = {"PENDING"};
        String start = formatter.format(LocalDateTime.of(2022, 9, 5, 0, 0, 0));
        String end = formatter.format(LocalDateTime.of(2022, 9, 12, 0, 0, 0));
        Pageable pageable = PageParam.createPageable(0, 10);
        ParametersAdminFindEvent param = new ParametersAdminFindEvent(usersId, states, catId,locIds, start, end, 0, 10);
        List<EventFullDto> expected = List.of(eventDtoMaper.toFulDto(event2));
        List<EventFullDto> result = service.findAllEvents(param);
        assertThat(expected.get(0), is(expected.get(0)));
    }

    @Test
    @DirtiesContext
    void test1_3findAllEvents_whenCategoryEmpty_user123_category1_stateWaiting() throws IncorrectPageValueException, NotValidParameterException, IllegalTimeException {
        data();
        Long[] usersId = {1L, 2L, 3L};
        Long[] catId = {1L};
        Long[] locIds = {1L};
        String[] states = {"PENDING"};
        String start = formatter.format(LocalDateTime.of(2022, 9, 5, 0, 0, 0));
        String end = formatter.format(LocalDateTime.of(2022, 9, 12, 0, 0, 0));
        Pageable pageable = PageParam.createPageable(0, 10);
        ParametersAdminFindEvent param = new ParametersAdminFindEvent(usersId, states, catId, locIds, start, end, 0, 10);
        List<EventFullDto> expected = List.of(eventDtoMaper.toFulDto(event2));
        List<EventFullDto> result = service.findAllEvents(param);
        assertThat(result.size(), is(0));
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
        Collection<User> participants = new ArrayList<>();
        participants.add(user4);

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
                new ArrayList<>());
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
                PENDING,
                "title",
                5,
                participants);
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