package ru.practicum.ewmmainservice.exceptions;

public class IlegalUserIdException extends Exception{
    String userId;
    String modelId;

    public String getUserId() {
        return userId;
    }

    public String getModelId() {
        return modelId;
    }

    public IlegalUserIdException(String message, String userId, String modelId) {
        super(message);
        this.userId = userId;
        this.modelId = modelId;
    }
}
