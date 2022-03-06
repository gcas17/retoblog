package com.bootcamp.reactive.retoblog.util.enums;

import lombok.Getter;

@Getter
public enum BlogStatus {
    ACTIVE("Activo"),
    INACTIVE("Inactivo");

    private final String description;

    BlogStatus(String description) {
        this.description = description;
    }
}
