package ru.practicum.ewmmainservice.adminService.user.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ru.practicum.ewmmainservice.adminService.user.UserAdminRepository;
import ru.practicum.ewmmainservice.exceptions.ModelAlreadyExistsException;
import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.models.user.User;
import ru.practicum.ewmmainservice.models.user.dto.NewUserDto;
import ru.practicum.ewmmainservice.models.user.dto.UserDto;
import ru.practicum.ewmmainservice.models.user.dto.UserDtoMaper;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserAdminServiceImplTest {
    UserDtoMaper dtoMaper = new UserDtoMaper();
    UserAdminRepository repository = Mockito.mock(UserAdminRepository.class);
    UserAdminServiceImpl service = new UserAdminServiceImpl(repository, dtoMaper);

    @Test
    void test1_1addNewUser() throws ModelAlreadyExistsException {
        NewUserDto newUserDto = new NewUserDto("Email@mail.ru", "name");
        dtoMaper.fromCreateDto(newUserDto);
        when(repository.save(dtoMaper.fromCreateDto(newUserDto)))
                .thenReturn(new User(1L, "Email@mail.ru", "name"));
        UserDto userDto = service.addNewUser(newUserDto);
        assertThat(userDto.getId(), is(1L));
        assertThat(userDto.getName(), is("name"));
        assertThat(userDto.getEmail(), is("Email@mail.ru"));
        Mockito.verify(repository, times(1)).save(dtoMaper.fromCreateDto(newUserDto));
    }
    @Test
    void test1_2addNewUser_userNotFound() throws ModelAlreadyExistsException {
        NewUserDto newUserDto = new NewUserDto("Email@mail.ru", "name");
        dtoMaper.fromCreateDto(newUserDto);
        when(repository.save(dtoMaper.fromCreateDto(newUserDto)))
                .thenThrow(DataIntegrityViolationException.class);
        assertThrows(ModelAlreadyExistsException.class, ()-> service.addNewUser(newUserDto));
    }

    @Test
    void test2_1deleteUser() throws NotFoundException {
        service.deleteUser(1L);
        Mockito.verify(repository,times(1)).deleteById(1L);
    }
    @Test
    void test2_2deleteUser_userNotFound() throws NotFoundException {
        Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(1L);
        assertThrows(NotFoundException.class,()-> service.deleteUser(1L));
    }

    @Test
    void test3_findAll() {
        User user =new User(1L,"email@mail.com", "name");
        PageImpl<User> page = new PageImpl<>(List.of(user));
        when(repository.findAll(PageRequest.of(0,2)))
                .thenReturn(page);
        Collection<UserDto> result = service.findAll(PageRequest.of(0,2));
        assertThat(result.stream().findFirst().get(), is(dtoMaper.toDto(user)));
    }
    @Test
    void test3_2findbyId() {
        User user =new User(1L,"email@mail.com", "name");
        Long[] ids = {1L};
        when(repository.findAllById(List.of(1L)))
                .thenReturn(List.of(user));
        Collection<UserDto> result = service.findByIds(ids);
        assertThat(result.stream().findFirst().get(), is(dtoMaper.toDto(user)));
    }
    @Test
    void test4_1findById() throws NotFoundException {
        User user = new User(1L, "email", "name");
        when(repository.findById(1L))
                .thenReturn(Optional.of(user));
        assertThat(service.findById(1L), is(user));
    }
    @Test
    void test4_2findById_whenUserNotFound() throws NotFoundException {
        User user = new User(1L, "email", "name");
        when(repository.findById(1L))
                .thenReturn(Optional.empty());
        assertThrows(NotFoundException.class,
                ()->service.findById(1L));
    }
}