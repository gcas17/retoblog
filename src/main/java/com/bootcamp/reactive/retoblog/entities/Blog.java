package com.bootcamp.reactive.retoblog.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "blogs")
public class Blog {
    @Id
    private String id;
    private String name;
    private String authorId;
    private String url;
    private String status;
}
