package ru.practicum.ewmmainservice.models.apiError;

import lombok.Builder;
import lombok.Value;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;
import java.util.List;
@Value
@Builder
public class ApiError {
   String description;
    /*:
    Сведения об ошибке
*/
    Object[] errors; // TODO: 20.09.2022 разобраться
    String message;
/*    example: Only pending or canceled events can be changed
    Сообщение об ошибке*/

   String reason;
   /* example: For the requested operation the conditions are not met.
    Общее описание причины ошибки
*/
    HttpStatus status;
   /* example: FORBIDDEN
    Код статуса HTTP-ответа*/

    String timestamp;
    /*example: 2022-06-09 06:27:23
    Дата и время когда произошла ошибка (в формате "yyyy-MM-dd HH:mm:ss")*/
}
