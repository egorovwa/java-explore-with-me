package ru.practicum.ewmmainservice.exceptions;

import java.util.Collection;

public class RelatedObjectsPresent extends Exception {
    final String type;
    final String reason;
    final Collection<Long> relatedObjects;

    public RelatedObjectsPresent(String message, String type, Collection<Long> relatedObjects) {
        super(message);
        this.type = type;
        this.relatedObjects = relatedObjects;
        reason = "The object has dependencies.";
    }

    public String getReason() {
        return reason;
    }

    public String getType() {
        return type;
    }

    public Collection<Long> getRelatedObjects() {
        return relatedObjects;
    }
}
