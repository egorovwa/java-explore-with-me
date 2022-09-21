package ru.practicum.ewmmainservice.exceptions;

import java.util.Collection;

public class RelatedObjectsPresent extends Exception{
    String type;
    Collection<Long> relatedObjects;

    public RelatedObjectsPresent(String message, String type, Collection<Long> relatedObjects) {
        super(message);
        this.type = type;
        this.relatedObjects = relatedObjects;
    }

    public String getType() {
        return type;
    }

    public Collection<Long> getRelatedObjects() {
        return relatedObjects;
    }
}
