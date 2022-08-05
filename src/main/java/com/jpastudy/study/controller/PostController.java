package com.jpastudy.study.controller;

import com.jpastudy.study.domain.CommentForm;
import com.jpastudy.study.domain.Post;
import com.jpastudy.study.domain.PostForm;
import com.jpastudy.study.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/post")
@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/list")
    public String list(Model model){
        List<Post> posts = postService.getList();
        model.addAttribute("posts", posts);
        return "post/list";
    }

    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Long id, CommentForm commentForm){
        Post post = postService.getPost(id);
        model.addAttribute("post", post);
        return "post/detail";
    }

    @GetMapping("/new")
    public String createForm(Model model){
        model.addAttribute("postForm", new PostForm());
        return "post/createPostForm";
    }

    @PostMapping("/new")
    public String create(@Valid PostForm form, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "post/createPostForm";
        }

        Post post = new Post();
        post.setCreateDate(LocalDateTime.now());
        post.setSubject(form.getSubject());
        post.setContent(form.getContent());

        postService.savePost(post);

        return "redirect:/post/list";
    }
}
