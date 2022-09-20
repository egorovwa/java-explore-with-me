package ru.practicum.ewmmainservice.adminService.user.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewmmainservice.adminService.user.UserAdminService;
import ru.practicum.ewmmainservice.adminService.user.UserAdminRepository;
import ru.practicum.ewmmainservice.exceptions.UserAlreadyExistsException;
import ru.practicum.ewmmainservice.exceptions.UserNotFoundException;
import ru.practicum.ewmmainservice.models.user.User;
import ru.practicum.ewmmainservice.models.user.dto.NewUserDto;
import ru.practicum.ewmmainservice.models.user.dto.UserDto;
import ru.practicum.ewmmainservice.models.user.dto.UserDtoMaper;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserAdminServiceImpl implements UserAdminService {
    private final UserAdminRepository repository;
    private final UserDtoMaper userDtoMaper;

    @Override
    public UserDto addNewUser(NewUserDto newUserDto) throws UserAlreadyExistsException {
        try {
            User user = repository.save(userDtoMaper.fromCreateDto(newUserDto));
            return userDtoMaper.toDto(user);
        } catch (DataIntegrityViolationException e) {
            throw new UserAlreadyExistsException(e.getMessage(), "email", newUserDto.getEmail());
        }
    }

    @Override
    public void deleteUser(Long userId) throws UserNotFoundException {
        try {
            repository.deleteById(userId);
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException(e.getMessage(), "Id", String.valueOf(userId));
        }
    }

    @Override
    public Collection<UserDto> findAll(Pageable pageable) {
        return repository.findAll(pageable).stream()
                .map(userDtoMaper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<UserDto> findByIds(Long[] ids) {
        return repository.findAllById(Arrays.asList(ids)).stream()
                .map(userDtoMaper::toDto)
                .collect(Collectors.toList());
    }

}
