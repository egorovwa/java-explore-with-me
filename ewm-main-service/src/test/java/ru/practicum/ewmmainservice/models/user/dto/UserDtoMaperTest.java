package ru.practicum.ewmmainservice.models.user.dto;

import org.junit.jupiter.api.Test;
import ru.practicum.ewmmainservice.models.user.User;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

class UserDtoMaperTest {
private final UserDtoMaper dtoMaper = new UserDtoMaper();
    @Test
    void fromCreateDto() {
        NewUserDto newUserDto = new NewUserDto("email", "name");
        User user = new User(null,"email", "name");
        assertThat(dtoMaper.fromCreateDto(newUserDto), is(user));

    }

    @Test
    void toDto() {
        User user = new User(1L,"email", "name");
        UserDto userDto=new UserDto(1L,"email", "name");
        assertThat(dtoMaper.toDto(user), is(userDto));
    }
}