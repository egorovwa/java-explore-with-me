package ru.practicum.ewmmainservice.models.parameters;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewmmainservice.adminService.category.CategoryService;
import ru.practicum.ewmmainservice.adminService.user.UserAdminService;
import ru.practicum.ewmmainservice.exceptions.IllegalTimeException;
import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.exceptions.NotValidParameterException;
import ru.practicum.ewmstatscontract.utils.Utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ParametersValidator {
    private final UserAdminService userAdminService;
    private final CategoryService categoryService;
    private final DateTimeFormatter formatter = Utils.getDateTimeFormater();

    public boolean adminFindEvents(ParametersAdminFindEvent parameters) throws NotValidParameterException, IllegalTimeException {
        if (parameters.getRangeStart() >= parameters.getRangeEnd()) {
            throw new IllegalTimeException("Конец должен быть после старта.",
                    String.format("statr %s end %s",
                            formatter.format(LocalDateTime.ofEpochSecond(parameters.getRangeStart(), 0, ZoneOffset.UTC)),
                            formatter.format(LocalDateTime.ofEpochSecond(parameters.getRangeEnd(), 0, ZoneOffset.UTC))));
        }
        List<ErrorParam> errors = new ArrayList<>();
        errors.addAll(usersValidation(parameters.getUsers()));
        errors.addAll(categoryValidation(parameters.getCategories()));
        if (!errors.isEmpty()) {
            throw new NotValidParameterException(String.format("%s parameters not valid", errors.size()), errors);
        }
        return true;
    }

    private Collection<ErrorParam> categoryValidation(List<Long> categories) {
        List<ErrorParam> categoryErrors = new ArrayList<>();
        categories.forEach(id -> {
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
        users.forEach(id -> {
            try {
                userAdminService.findById(id);
            } catch (NotFoundException e) {
                userErrors.add(new ErrorParam("User", "id", id.toString()));
            }
        });
        return userErrors;
    }


}
