package com.bootcamp.reactive.retoblog.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(value="users")
public class User {
    @Id
    private String id;
    private String login;
    private String password;
    private String authorId;
    private List<Comment> comments;
}
