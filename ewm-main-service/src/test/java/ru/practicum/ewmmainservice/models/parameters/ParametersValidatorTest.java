package ru.practicum.ewmmainservice.models.parameters;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.ewmmainservice.adminservice.category.CategoryService;
import ru.practicum.ewmmainservice.adminservice.user.UserAdminService;
import ru.practicum.ewmmainservice.exceptions.IllegalTimeException;
import ru.practicum.ewmmainservice.exceptions.IncorrectPageValueException;
import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.exceptions.NotValidParameterException;
import ru.practicum.ewmmainservice.models.category.Category;
import ru.practicum.ewmmainservice.models.user.User;
import ru.practicum.ewmstatscontract.utils.Utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ParametersValidatorTest {
    @Mock
    private UserAdminService userAdminService;
    @Mock
    private CategoryService categoryService;
    DateTimeFormatter formatter = Utils.getDateTimeFormatter();
    @InjectMocks
    private ParametersValidator validator;

    @Test
    void adminFindEvents_isValid() throws IncorrectPageValueException, NotFoundException, NotValidParameterException, IllegalTimeException {
        Category category1 = new Category(1L, "category1");
        Category category2 = new Category(2L, "category2");
        Category category3 = new Category(3L, "category3");
        User user1 = new User(1L, "email@mail.ru", "name");
        User user2 = new User(2L, "emai@rrr.ru", "name2");
        User user3 = new User(3L, "sss@sss.fff", "name3");
        User user4 = new User(4L, "sss@sssl4.fff", "name4");
        Long[] usersId = {1L, 2L};
        Long[] catId = {1L, 2L};
        String[] states = {"PENDING", "CANCELED"};
        String start = formatter.format(LocalDateTime.now());
        String end = formatter.format(LocalDateTime.now().plusHours(1));
        ParametersAdminFindEvent param = new ParametersAdminFindEvent(usersId, states, catId, start, end, 0, 10);

        when(userAdminService.findById(1L)).thenReturn(user1);
        when(userAdminService.findById(2L)).thenReturn(user2);
        when(categoryService.findByid(1L)).thenReturn(category1);
        when(categoryService.findByid(2L)).thenReturn(category2);
        assertTrue(validator.adminFindEvents(param));
    }

    @Test
    void adminFindEvents_whenThrows() throws IncorrectPageValueException, NotFoundException, NotValidParameterException {
        Category category1 = new Category(1L, "category1");
        Category category2 = new Category(2L, "category2");
        Category category3 = new Category(3L, "category3");
        User user1 = new User(1L, "email@mail.ru", "name");
        User user2 = new User(2L, "emai@rrr.ru", "name2");
        User user3 = new User(3L, "sss@sss.fff", "name3");
        User user4 = new User(4L, "sss@sssl4.fff", "name4");
        Long[] usersId = {1L, 2L};
        Long[] catId = {1L, 2L};
        String[] states = {"PENDING", "CANCELED"};
        String start = formatter.format(LocalDateTime.now());
        String end = formatter.format(LocalDateTime.now().plusHours(1));
        ParametersAdminFindEvent param = new ParametersAdminFindEvent(usersId, states, catId, start, end, 0, 10);

        when(userAdminService.findById(1L)).thenReturn(user1);
        when(userAdminService.findById(2L)).thenThrow(new NotFoundException("id", "1", "User"));
        when(categoryService.findByid(1L)).thenReturn(category1);
        when(categoryService.findByid(2L)).thenThrow(new NotFoundException("id", "2", "Category"));
        NotValidParameterException e = assertThrows(NotValidParameterException.class, () -> validator.adminFindEvents(param));
        assertThat(e.getExceptionList(), is(List.of(new ErrorParam("User", "id", "2"),
                new ErrorParam("Category", "id", "2"))));
        assertThat(e.getMessage(), is("2 parameters not valid"));
    }

    @Test
    void adminFindEvents_illegalTime() throws IncorrectPageValueException, NotFoundException, NotValidParameterException, IllegalTimeException {
        Long[] usersId = {1L, 2L};
        Long[] catId = {1L, 2L};
        String[] states = {"PENDING", "CANCELED"};
        String start = formatter.format(LocalDateTime.now());
        String end = formatter.format(LocalDateTime.now().minusSeconds(1));
        ParametersAdminFindEvent param = new ParametersAdminFindEvent(usersId, states, catId, start, end, 0, 10);
        assertThrows(IllegalTimeException.class, () -> validator.adminFindEvents(param));
    }

}

