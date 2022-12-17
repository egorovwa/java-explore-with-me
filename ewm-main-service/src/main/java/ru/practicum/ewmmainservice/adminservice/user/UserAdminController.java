package ru.practicum.ewmmainservice.adminservice.user;

import com.example.evmdtocontract.dto.user.NewUserDto;
import com.example.evmdtocontract.dto.user.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmainservice.exceptions.IncorrectPageValueException;
import ru.practicum.ewmmainservice.exceptions.ModelAlreadyExistsException;
import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.utils.PageParam;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
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
    public Collection<UserDto> findAll(@PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                       @Positive @RequestParam(name = "size", defaultValue = "10") Integer size,
                                       @RequestParam(value = "ids", required = false) Long[] ids) throws IncorrectPageValueException {
        var pageable = PageParam.createPageable(from, size);
        if (ids == null) {
            return userAdminService.findAll(pageable);
        } else {
            return userAdminService.findByIds(ids, pageable);
        }
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable("userId") Long userId) throws NotFoundException {
        userAdminService.deleteUser(userId);
    }
}
