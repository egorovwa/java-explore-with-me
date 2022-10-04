package ru.practicum.ewmmainservice.adminService.user.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewmmainservice.adminService.user.UserAdminRepository;
import ru.practicum.ewmmainservice.adminService.user.UserAdminService;
import ru.practicum.ewmmainservice.exceptions.ModelAlreadyExistsException;
import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.models.user.User;
import ru.practicum.ewmmainservice.models.user.dto.NewUserDto;
import ru.practicum.ewmmainservice.models.user.dto.UserDto;
import ru.practicum.ewmmainservice.models.user.dto.UserDtoMapper;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserAdminServiceImpl implements UserAdminService {
    private final UserAdminRepository repository;
    private final UserDtoMapper userDtoMapper;

    @Override
    public UserDto addNewUser(NewUserDto newUserDto) throws ModelAlreadyExistsException {
        try {
            User user = repository.save(userDtoMapper.fromCreateDto(newUserDto));
            log.info("Create new User {}", user);
            return userDtoMapper.toDto(user);
        } catch (DataIntegrityViolationException e) {
            log.warn("Created user with email = {}, alredy exist.", newUserDto.getEmail());
            throw new ModelAlreadyExistsException("email", newUserDto.getEmail(), "User");
        }
    }

    @Override
    public void deleteUser(Long userId) throws NotFoundException {
        try {
            log.info("Delete user id {}", userId);
            repository.deleteById(userId);
        } catch (EmptyResultDataAccessException e) {
            log.warn("Deleted user not found id = {}", userId);
            throw new NotFoundException("Id", String.valueOf(userId), "User");
        }
    }

    @Override
    public Collection<UserDto> findAll(Pageable pageable) {
        log.info("Search for all users. With pageble: {}", pageable);
        return repository.findAll(pageable).stream()
                .map(userDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<UserDto> findByIds(Long[] ids, Pageable pageable) {
        log.info("Search users by ids {}", Arrays.asList(ids));
        return repository.findAllById(Arrays.asList(ids), pageable).stream()
                .map(userDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public User findById(Long userId) throws NotFoundException {
        log.debug("Find user id = {}", userId);
        return repository.findById(userId)
                .orElseThrow(() -> new NotFoundException("id", userId.toString(), "User"));
    }

}
