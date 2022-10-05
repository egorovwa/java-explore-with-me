package ru.practicum.ewmmainservice.exceptions;

public class IlegalUserIdException extends Exception {
    final Long userId;
    final Long modelId;
    final String className;
    final String reason;

    public IlegalUserIdException(Long userId, Long modelId, String className) {
        super(String.format("The user id = %s does not have access to the %s id = %s.",
                userId, className, modelId));
        this.userId = userId;
        this.modelId = modelId;
        this.className = className;
        reason = "The User does not have access to the requested Object.";
    }

    public IlegalUserIdException(String message, Long userId, Long modelId, String className) {
        super(message);
        this.userId = userId;
        this.modelId = modelId;
        this.className = className;
        reason = "This user cannot perform the requested actions.";
    }
}
