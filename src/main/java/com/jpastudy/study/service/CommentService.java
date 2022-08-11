package com.jpastudy.study.service;

import com.jpastudy.study.domain.Comment;
import com.jpastudy.study.domain.Member;
import com.jpastudy.study.domain.Post;
import com.jpastudy.study.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public Comment create(Post post, String content, Member member){

        Comment comment = new Comment();

        comment.setContent(content);
        comment.setCreateDate(LocalDateTime.now());
        comment.setPost(post);
        comment.setAuthor(member);

        commentRepository.save(comment);

        return comment;
    }

}
