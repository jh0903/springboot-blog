package com.jpastudy.study.controller;

import com.jpastudy.study.domain.CommentForm;
import com.jpastudy.study.domain.Member;
import com.jpastudy.study.domain.Post;
import com.jpastudy.study.service.CommentService;
import com.jpastudy.study.service.MemberService;
import com.jpastudy.study.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;

@RequestMapping("/comment")
@RequiredArgsConstructor
@Controller
public class CommentController {

    private final PostService postService;
    private final CommentService commentService;
    private final MemberService memberService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String createComment(Model model, @PathVariable("id") Long id,
                                @Valid CommentForm commentForm, BindingResult bindingResult,
                                Principal principal){
        Post post = postService.getPost(id);
        Member member = memberService.getMember(principal.getName());

        if(bindingResult.hasErrors()){
            model.addAttribute("post", post);
            return "post/detail";
        }

        commentService.create(post, commentForm.getContent(), member);
        return String.format("redirect:/post/detail/%s", id);
    }
}
