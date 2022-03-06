package com.bootcamp.reactive.retoblog.util.enums;

import lombok.Getter;

@Getter
public enum CommentStatus {
    ACTIVE("Activo"),
    INACTIVE("Inactivo");

    private final String description;

    CommentStatus(String description) {
        this.description = description;
    }
}
