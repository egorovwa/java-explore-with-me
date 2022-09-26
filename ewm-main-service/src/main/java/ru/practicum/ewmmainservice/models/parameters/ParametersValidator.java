package ru.practicum.ewmmainservice.models.parameters;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewmmainservice.adminService.category.CategoryService;
import ru.practicum.ewmmainservice.adminService.user.UserAdminRepository;
import ru.practicum.ewmmainservice.adminService.user.UserAdminService;
import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.exceptions.NotValidParameterException;
import ru.practicum.ewmmainservice.exceptions.RuntimeNotFoundException;

import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ParametersValidator {
    private final UserAdminService userAdminService;
    private final CategoryService categoryService;
    public void adminFindEvents(ParametersAdminFindEvent parameters) throws NotValidParameterException {
       List<ErrorParam> errors = new ArrayList<>(); // TODO: 25.09.2022 state ?
       errors.addAll(usersValidation(parameters.getUsers()));
       errors.addAll(categoryValidation(parameters.getCategories()));
       if (!errors.isEmpty()){
           throw new NotValidParameterException(String.format("%s parameters not valid", errors.size()),errors);
       }
    }

    private Collection<ErrorParam> categoryValidation(List<Long> categories) {
        List<ErrorParam> categoryErrors = new ArrayList<>();
        categories.forEach(id->{
            try {
                categoryService.findByid(id);
            } catch (NotFoundException e) {
                categoryErrors.add(new ErrorParam("Category", "id", id.toString()));
            }
        });
        return categoryErrors;
    }

    private List<ErrorParam> usersValidation(List<Long> users) {
        List<ErrorParam> userErrors = new ArrayList<>();
        users.forEach(id->{
            try {
                userAdminService.findById(id);
            } catch (NotFoundException e) {
                userErrors.add(new ErrorParam("User", "id", id.toString()));
            }
        });
        return userErrors;
    }


}
