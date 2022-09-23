package ru.practicum.ewmmainservice.exceptions;

public class NumberParticipantsExceededException extends Exception{
    Integer maxCount;

    public Integer getMaxCount() {
        return maxCount;
    }

    public NumberParticipantsExceededException(String message, Integer maxCount) {
        super(message);
        this.maxCount = maxCount;
    }
}
