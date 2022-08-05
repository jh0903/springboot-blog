package com.jpastudy.study.service;

import com.jpastudy.study.DataNotFoundException;
import com.jpastudy.study.domain.Post;
import com.jpastudy.study.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public void savePost(Post post){
        postRepository.save(post);
    }

    public List<Post> getList(){
        return postRepository.findAll();
    }

    public Post getPost(Long id){
        Post post = postRepository.find(id);
        if(post!=null){
            return post;
        }
        else {
            throw new DataNotFoundException("question not found");
        }
    }


}
