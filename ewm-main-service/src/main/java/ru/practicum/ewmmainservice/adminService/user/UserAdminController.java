package ru.practicum.ewmmainservice.adminService.user;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmainservice.utils.PageParam;
import ru.practicum.ewmmainservice.exceptions.IncorrectPageValueException;
import ru.practicum.ewmmainservice.exceptions.ModelAlreadyExistsException;
import ru.practicum.ewmmainservice.exceptions.UserNotFoundException;
import ru.practicum.ewmmainservice.models.user.dto.NewUserDto;
import ru.practicum.ewmmainservice.models.user.dto.UserDto;


import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.websocket.server.PathParam;
import java.util.Collection;

@RestController
@Validated
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class UserAdminController {
    private final UserAdminService userAdminService;
    @PostMapping
    public UserDto addNewUser(@Valid @RequestBody NewUserDto newUserDto) throws ModelAlreadyExistsException {
        return userAdminService.addNewUser(newUserDto);
    }
    @GetMapping
    public Collection<UserDto> findAll(@PositiveOrZero @RequestParam(name ="from", defaultValue = "0") Integer from,
                                       @Positive @RequestParam(name = "size", defaultValue = "10") Integer size,
                                       @PathParam("ids") Long[] ids) throws IncorrectPageValueException {
        if (ids == null){
        return userAdminService.findAll(PageParam.createPageable(from, size));
        }else {
            return userAdminService.findByIds(ids);
        }
    }
    @DeleteMapping("/{userId}")
    public void delete(@PathVariable("userId") Long userId) throws UserNotFoundException {
        userAdminService.deleteUser(userId);
    }
}
