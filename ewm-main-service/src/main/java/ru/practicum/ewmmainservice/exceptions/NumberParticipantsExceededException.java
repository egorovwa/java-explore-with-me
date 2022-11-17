package ru.practicum.ewmmainservice.exceptions;

public class NumberParticipantsExceededException extends Exception {
    final Integer maxCount;
    final String reason = "The limit of participants has been exceeded.";

    public NumberParticipantsExceededException(Integer maxCount) {
        super(String.format("The limit of participants has been exceeded. Max = %s", maxCount));

        this.maxCount = maxCount;
    }

    public Integer getMaxCount() {
        return maxCount;
    }

    public String getReason() {
        return reason;
    }
}
