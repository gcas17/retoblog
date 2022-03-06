package com.bootcamp.reactive.retoblog.util.enums;

public enum ReactionType {
    LIKE("like");

    private final String description;

    ReactionType(String description) {
        this.description = description;
    }
}
