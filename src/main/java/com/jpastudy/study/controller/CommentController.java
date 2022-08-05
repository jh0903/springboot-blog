package com.jpastudy.study.controller;

import com.jpastudy.study.domain.CommentForm;
import com.jpastudy.study.domain.Post;
import com.jpastudy.study.service.CommentService;
import com.jpastudy.study.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@RequestMapping("/comment")
@RequiredArgsConstructor
@Controller
public class CommentController {

    private final PostService postService;
    private final CommentService commentService;

    @PostMapping("/create/{id}")
    public String createComment(Model model, @PathVariable("id") Long id,
                                @Valid CommentForm commentForm, BindingResult bindingResult){
        Post post = postService.getPost(id);
        if(bindingResult.hasErrors()){
            model.addAttribute("post", post);
            return "post/detail";
        }

        commentService.create(post, commentForm.getContent());
        return String.format("redirect:/post/detail/%s", id);
    }
}
