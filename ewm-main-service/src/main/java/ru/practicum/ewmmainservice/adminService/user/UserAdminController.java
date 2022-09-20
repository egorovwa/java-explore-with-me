package ru.practicum.ewmmainservice.adminService.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmainservice.utils.PageParam;
import ru.practicum.ewmmainservice.exceptions.IncorrectPageValueException;
import ru.practicum.ewmmainservice.exceptions.UserAlreadyExistsException;
import ru.practicum.ewmmainservice.exceptions.UserNotFoundException;
import ru.practicum.ewmmainservice.models.user.dto.NewUserDto;
import ru.practicum.ewmmainservice.models.user.dto.UserDto;


import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.Collection;

@RestController
@Slf4j
@Validated
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class UserAdminController {
    private final UserAdminService userAdminService;
    @PostMapping
    public UserDto addNewUser(@Valid @RequestBody NewUserDto newUserDto) throws UserAlreadyExistsException {
        return userAdminService.addNewUser(newUserDto);
    }
    @GetMapping
    public Collection<UserDto> findAll(@PathParam("from") Integer from,
                                       @PathParam("size") Integer size) throws IncorrectPageValueException {
        return userAdminService.findAll(PageParam.createPageable(from, size));
    }
    @DeleteMapping("/{userId}")
    public void delete(@PathVariable("userId") Long userId) throws UserNotFoundException {
        userAdminService.deleteUser(userId);
    }
}
