package ru.practicum.ewmmainservice.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.practicum.ewmmainservice.exceptions.IncorrectPageValueException;

public class PageParam {
    public static Pageable createPageable(Integer from, Integer size, String sortColumn)
            throws IncorrectPageValueException {
        if (from == null || size == null) {
            return null;
        } else {
            if (from < 0 || size <= 0) {
                throw new IncorrectPageValueException("The values should not be less than 0",
                        "from/size", String.valueOf(from));
            }
            Sort sort = Sort.by(sortColumn).descending();
            return PageRequest.of(from / size, size, sort);
        }
    }

    public static Pageable createPageable(Integer from, Integer size) throws IncorrectPageValueException {
        if (from == null || size == null) {
            return null;
        } else {
            if (from < 0 || size <= 0) {
                throw new IncorrectPageValueException("The values should not be less than 0",
                        "from/size", String.valueOf(from));
            }
            return PageRequest.of(from / size, size);
        }
    }
}
