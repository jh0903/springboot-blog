package com.jpastudy.study;

import com.jpastudy.study.domain.Member;
import com.jpastudy.study.domain.Post;
import com.jpastudy.study.repository.CommentRepository;
import com.jpastudy.study.repository.MemberRepository;
import com.jpastudy.study.repository.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    CommentRepository commentRepository;

    @Test
    @Transactional
    public void testMember() throws Exception{
        Member member = new Member();
        member.setUsername("a");

        Long savedId = memberRepository.save(member);
        Member findMember = memberRepository.find(savedId);

        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
    }



    public void createPost(){
        Post post = new Post();
        post.setSubject("hi");
        post.setContent("hello~");
        post.setCreateDate(LocalDateTime.now());
        postRepository.save(post);
    }
}