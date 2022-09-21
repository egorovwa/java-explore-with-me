package ru.practicum.ewmmainservice.privateservise.participationRequest;

import java.time.LocalDateTime;

public class ParticipationRequestDto {

    LocalDateTime created;
    /*	string
    example: 2022-09-06T21:10:05.432
    Дата и время создания заявки*/
    Long event;
    /*	integer($int64)
    example: 1
    Идентификатор события*/

    Long id;	/*integer($int64)
    example: 3
    Идентификатор заявки*/

    Long requester;	/*integer($int64)
    example: 2
    Идентификатор пользователя, отправившего заявку*/

    RequestStatus status;	/*string
    example: PENDING
    Статус заявки*/
}
