package ru.practicum.ewmmainservice.adminservice.user;

import com.example.evmdtocontract.dto.user.NewUserDto;
import com.example.evmdtocontract.dto.user.UserDto;
import org.springframework.data.domain.Pageable;
import ru.practicum.ewmmainservice.exceptions.ModelAlreadyExistsException;
import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.models.user.User;

import java.util.Collection;

public interface UserAdminService {
    UserDto addNewUser(NewUserDto newUserDto) throws ModelAlreadyExistsException;

    void deleteUser(Long userId) throws NotFoundException;

    Collection<UserDto> findAll(Pageable pageable);


    Collection<UserDto> findByIds(Long[] ids, Pageable pageable);

    User findById(Long userId) throws NotFoundException;
}
