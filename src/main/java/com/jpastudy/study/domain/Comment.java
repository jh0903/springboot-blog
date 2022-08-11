package com.jpastudy.study.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Getter @Setter
public class Comment {

    @Id @GeneratedValue
    private Long id;

    private String content;

    @CreatedDate
    private LocalDateTime createDate;

    @ManyToOne
    @JoinColumn(name="post_id")
    private Post post;

    @ManyToOne
    private Member author;

    public void setPost(Post post){
        System.out.println(post.getId());
        this.post = post;
        post.getCommentList().add(this);
    }
}
