package com.bootcamp.reactive.retoblog.services.impl;

import com.bootcamp.reactive.retoblog.core.exception.PostBadRequestException;
import com.bootcamp.reactive.retoblog.entities.Comment;
import com.bootcamp.reactive.retoblog.entities.Post;
import com.bootcamp.reactive.retoblog.repositories.BlogRepository;
import com.bootcamp.reactive.retoblog.repositories.CommentRepository;
import com.bootcamp.reactive.retoblog.repositories.PostRepository;
import com.bootcamp.reactive.retoblog.services.PostService;
import com.bootcamp.reactive.retoblog.util.enums.BlogStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Mono<Post> save(Post post) {
        return this.postRepository.findByBlogId(post.getBlogId())
                .collectList()
                .flatMap(posts -> {
                            boolean isSameDayPosted = false;
                            for (Post item: posts) {
                                if (validateSameDayPostByBlog(
                                        item.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                                        post.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                                )) {
                                    isSameDayPosted = true;
                                }
                            }

                            return isSameDayPosted ? Mono.error(new PostBadRequestException("Solo se puede publicar un post por día"))
                                    : this.blogRepository.findById(post.getBlogId());
                        }
                ).flatMap(
                        blog -> !blog.getStatus().toLowerCase().equals(BlogStatus.INACTIVE.getDescription().toLowerCase())
                        ? this.postRepository.save(post)
                        : Mono.error(new PostBadRequestException("Solo se puede registrar posts en blogs en estado activo")));
    }

    @Override
    public Flux<Post> findAll() {
        return this.postRepository.findAll().map(
                post -> {
                    List<Comment> commentList = new ArrayList<>();
                    this.commentRepository.findByPostId(post.getId()).collectList().subscribe(commentList::addAll);
                    post.setComments(commentList);
                    return post;
                }
        );
    }

    @Override
    public Mono<Void> deleteByAuthorId(String authorId) {
        return this.blogRepository.findByAuthorId(authorId)
                .flatMap(blog -> this.postRepository.findByBlogId(blog.getId()))
                .flatMap(post -> this.postRepository.deleteById(post.getId()))
                .then(Mono.empty());
    }

    @Override
    public Flux<Post> findByBlogId(String blogId) {
        return this.postRepository.findByBlogId(blogId).map(
                post -> {
                    List<Comment> commentList = new ArrayList<>();
                    this.commentRepository.findByPostId(post.getId()).collectList().subscribe(commentList::addAll);
                    post.setComments(commentList);
                    return post;
                }
        );
    }

    private boolean validateSameDayPostByBlog(LocalDate fecha1, LocalDate fecha2) {
        Period period = Period.between(fecha1, fecha2);
        return period.getYears() == 0 && period.getMonths() == 0 && period.getDays() == 0;
    }
}
