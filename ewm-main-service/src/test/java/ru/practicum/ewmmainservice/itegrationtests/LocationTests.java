package ru.practicum.ewmmainservice.itegrationtests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.ewmmainservice.adminservice.category.CategoryService;
import ru.practicum.ewmmainservice.adminservice.event.AdminEventService;
import ru.practicum.ewmmainservice.adminservice.location.AdminLocationService;
import ru.practicum.ewmmainservice.adminservice.user.UserAdminService;
import ru.practicum.ewmmainservice.exceptions.*;
import ru.practicum.ewmmainservice.models.category.dto.CategoryDto;
import ru.practicum.ewmmainservice.models.category.dto.NewCategoryDto;
import ru.practicum.ewmmainservice.models.event.dto.EventDtoMaper;
import ru.practicum.ewmmainservice.models.event.dto.EventFullDto;
import ru.practicum.ewmmainservice.models.event.dto.NewEventDto;
import ru.practicum.ewmmainservice.models.location.dto.LocationFullDto;
import ru.practicum.ewmmainservice.models.location.dto.NewLocationDto;
import ru.practicum.ewmmainservice.models.parameters.ParametersPublicEventFind;
import ru.practicum.ewmmainservice.models.user.User;
import ru.practicum.ewmmainservice.models.user.dto.NewUserDto;
import ru.practicum.ewmmainservice.models.user.dto.UserDto;
import ru.practicum.ewmmainservice.models.user.dto.UserDtoMapper;
import ru.practicum.ewmmainservice.privateservise.event.PrivateEventService;
import ru.practicum.ewmmainservice.privateservise.location.LocationService;
import ru.practicum.ewmmainservice.publicservice.event.PublicEventService;
import ru.practicum.ewmmainservice.publicservice.event.impl.PublicEventServiceImpl;
import ru.practicum.ewmstatscontract.utils.Utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;


public class LocationTests extends IntegrationTestsBase {
    @Autowired
    UserAdminService userAdminService;
    @Autowired
    PrivateEventService privateEventService;
    @Autowired
    AdminEventService adminEventService;
    @Autowired
    AdminLocationService adminLocationService;
    @Autowired
    PublicEventService publicEventService;
    @Autowired
    LocationService locationService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    UserDtoMapper userDtoMapper;

    DateTimeFormatter formatter = Utils.getDateTimeFormatter();
    @Autowired
    EventDtoMaper eventDtoMaper;

    @Test
    @DirtiesContext
    void test1_adminCreateLocations() throws ModelAlreadyExistsException, NotFoundException, LocationException {
        UserDto userDto1 = userAdminService.addNewUser(new NewUserDto("user1@mail.com", "user1"));

        NewLocationDto zooNewLocation = new NewLocationDto("Zoo", 53.35732787593802, 83.6831706292003, 360, null);
        NewLocationDto barnaulNewLocation = new NewLocationDto("Barnaul", 53.32749399827193, 83.68262129379748, 14280, null);
        LocationFullDto zoo = adminLocationService.adminCreateLocation(zooNewLocation);
        LocationFullDto barnaul = adminLocationService.adminCreateLocation(barnaulNewLocation);

        assertThat(adminLocationService.findById(zoo.getId()).getParentId(), is(barnaul.getId())); // поиск дочерних локаций

        NewLocationDto parkNewLocation = new NewLocationDto("Park", 53.32494249307739, 83.7923472279321, 1066, barnaul.getId());
        LocationFullDto park = adminLocationService.adminCreateLocation(parkNewLocation);

        assertTrue(adminLocationService.findById(barnaul.getId()).getChilds().contains(park)); //добавление в список родителя

        NewLocationDto housInParkNewDto = new NewLocationDto("Hous in park", 53.31778479718983, 83.79063141645065, null, null);
        LocationFullDto housInPark = adminLocationService.adminCreateLocation(housInParkNewDto);
        assertThat(housInPark.getRadius(), is(10)); // минимальный радиус 10м.
        assertThat(housInPark.getParentId(), is(park.getId())); // поиск родительской локации

        NewLocationDto withBadParrent = new NewLocationDto("name", 53.31779479718983, 83.79063141845065, 50, zoo.getId());
        assertThrows(LocationException.class, () -> adminLocationService.adminCreateLocation(withBadParrent)); //неверно указана родительская локация

    }

    @Test
    @DirtiesContext
    void test2_userCreate2LocationAdminApprovedAndReject() throws ModelAlreadyExistsException, NotFoundException, LocationException, NotRequiredException {
        UserDto userDto1 = userAdminService.addNewUser(new NewUserDto("user1@mail.com", "user1"));
        NewLocationDto zooNewLocation = new NewLocationDto("Zoo", 53.35732787593802, 83.6831706292003, 360, null);
        NewLocationDto barnaulNewLocation = new NewLocationDto("Barnaul", 53.32749399827193, 83.68262129379748, 14280, null);
        LocationFullDto zoo = adminLocationService.adminCreateLocation(zooNewLocation);
        LocationFullDto barnaul = adminLocationService.adminCreateLocation(barnaulNewLocation);
        NewLocationDto userPointToApproved = new NewLocationDto("approved", 53.334804, 83.656906, null, null);
        NewLocationDto userPointToReject = new NewLocationDto("reject", 53.588368, 83.518394, null, null);
        LocationFullDto approved = locationService.createLocation(userPointToApproved, userDto1.getId());
        LocationFullDto reject = locationService.createLocation(userPointToReject, userDto1.getId());

        assertThat(approved.getApproved(), is(false)); //location создана не одобренна
        adminLocationService.approvedLocation(approved.getId());
        assertThat(locationService.findLocationById(approved.getId()).getApproved(), is(true)); // локация одобренна

        adminLocationService.rejectLocation(reject.getId());
        assertThrows(NotFoundException.class, () -> locationService.findLocationById(reject.getId())); // location отклонена и удаленна

    }

    @Test
    void test3_findEvent() throws ModelAlreadyExistsException, NotFoundException, LocationException, FiledParamNotFoundException, StatusException, IllegalTimeException, IncorrectPageValueException {
        NewLocationDto zooNewLocation = new NewLocationDto("Zoo", 53.35732787593802, 83.6831706292003, 360, null);
        NewLocationDto barnaulNewLocation = new NewLocationDto("Barnaul", 53.32749399827193, 83.68262129379748, 14280, null);
        LocationFullDto barnaul = adminLocationService.adminCreateLocation(barnaulNewLocation);
        NewLocationDto parkNewLocation = new NewLocationDto("Park", 53.32494249307739, 83.7923472279321, 1066, barnaul.getId());
        NewLocationDto housInParkNewDto = new NewLocationDto("Hous in park", 53.31778479718983, 83.79063141645065, null, null);

        LocationFullDto zoo = adminLocationService.adminCreateLocation(zooNewLocation);
        LocationFullDto park = adminLocationService.adminCreateLocation(parkNewLocation);
        LocationFullDto housInPark = adminLocationService.adminCreateLocation(housInParkNewDto);
        NewCategoryDto newCategoryDto = new NewCategoryDto();
        newCategoryDto.setName("category");
        CategoryDto categoryDto = categoryService.createCategory(newCategoryDto);
        NewEventDto newEventDto1 = new NewEventDto();
        newEventDto1.setLocation(housInPark.getId());
        newEventDto1.setCategory(categoryDto.getId());
        newEventDto1.setEventDate(formatter.format(LocalDateTime.now().plusDays(3)));
        newEventDto1.setPaid(true);
        newEventDto1.setDescription("12345678901234567890123");
        newEventDto1.setAnnotation("1234567890123456789012");
        newEventDto1.setTitle("Event1");
        newEventDto1.setRequestModeration(false);
        NewUserDto newUserDto = new NewUserDto("user1@fff.com", "nameUser");
        UserDto userDto1 = userAdminService.addNewUser(newUserDto);
        EventFullDto event1 = privateEventService.createEvent(userDto1.getId(), newEventDto1);
        adminEventService.publishEvent(event1.getId());
        Long[] locIds = {housInPark.getId()};
        ParametersPublicEventFind paramInLocation = new ParametersPublicEventFind(null,
                null,
                locIds,
                null,
                null,
                null,
                false,
                "VIEWS",
                0, 10,
                "0.0.0.0",
                "path"
                , false
        );
        assertThat(publicEventService.findEvents(paramInLocation).stream().findFirst().get().getAnnotation(), //поик в локации
                is(event1.getAnnotation()));


        Long[] locIds2 = {barnaul.getId()};
        ParametersPublicEventFind paramInBarnaulWithChilds = new ParametersPublicEventFind(null,
                null,
                locIds2,
                null,
                null,
                null,
                false,
                "VIEWS",
                0, 10,
                "0.0.0.0",
                "path"
                , true
        );
        assertThat(publicEventService.findEvents(paramInLocation).stream().findFirst().get().getAnnotation(), //поик в родительской локации
                is(event1.getAnnotation()));
    }

}
