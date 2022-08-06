package com.jpastudy.study;

import com.jpastudy.study.domain.Post;
import com.jpastudy.study.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;

@SpringBootTest
public class ApplicationTest {

    @Autowired
    private PostService postService;

    @Test
    void testJpa(){
        for(int i=1;i<=300;i++){
            String subject = String.format("테스트 데이터입니다:[%03d]", i);
            String content = "empty";
            Post post = new Post();
            post.setSubject(subject);
            post.setContent(content);
            post.setCreateDate(LocalDateTime.now());
            postService.savePost(post);
        }
    }
}
