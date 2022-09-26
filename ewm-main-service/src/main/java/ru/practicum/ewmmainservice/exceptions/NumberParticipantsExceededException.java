package ru.practicum.ewmmainservice.exceptions;

public class NumberParticipantsExceededException extends Exception{
    Integer maxCount;

    public Integer getMaxCount() {
        return maxCount;
    }

    public NumberParticipantsExceededException(Integer maxCount) {
        super(String.format("The limit of participants has been exceeded. Max = %s", maxCount));
        this.maxCount = maxCount;
    }
}
