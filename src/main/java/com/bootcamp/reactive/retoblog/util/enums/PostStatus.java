package com.bootcamp.reactive.retoblog.util.enums;

import lombok.Getter;

@Getter
public enum PostStatus {
    DRAFT("Borrador"),
    PUBLISHED("Publicado");

    private final String description;

    PostStatus(String description) {
        this.description = description;
    }
}
