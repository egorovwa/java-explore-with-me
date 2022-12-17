package ru.practicum.ewmmainservice.models.user.dto;

import com.example.evmdtocontract.dto.user.NewUserDto;
import com.example.evmdtocontract.dto.user.UserDto;
import com.example.evmdtocontract.dto.user.UserShortDto;
import org.junit.jupiter.api.Test;
import ru.practicum.ewmmainservice.models.user.User;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class UserDtoMapperTest {
    private final UserDtoMapper dtoMaper = new UserDtoMapper();

    @Test
    void fromCreateDto() {
        NewUserDto newUserDto = new NewUserDto("email", "name");
        User user = new User(null, "email", "name");
        assertThat(dtoMaper.fromCreateDto(newUserDto), is(user));

    }

    @Test
    void toDto() {
        User user = new User(1L, "email", "name");
        UserDto userDto = new UserDto(1L, "email", "name");
        assertThat(dtoMaper.toDto(user), is(userDto));
    }

    @Test
    void toShortDto() {
        User user = new User(1L, "emaik@mail.com", "name");
        UserShortDto dto = new UserShortDto(1L, "name");
        assertThat(dtoMaper.toShortDto(user), is(dto));
    }
}