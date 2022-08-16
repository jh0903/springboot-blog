package com.jpastudy.study.controller;

import com.jpastudy.study.domain.Comment;
import com.jpastudy.study.domain.CommentForm;
import com.jpastudy.study.domain.Member;
import com.jpastudy.study.domain.Post;
import com.jpastudy.study.service.CommentService;
import com.jpastudy.study.service.MemberService;
import com.jpastudy.study.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

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

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String modifyComment(CommentForm commentForm, @PathVariable("id") Long id,
                                Principal principal){
        Comment comment = commentService.getComment(id);
        if(!principal.getName().equals(comment.getAuthor().getUsername())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정권한이 없습니다.");
        }

        commentForm.setContent(comment.getContent());
        return "comment_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String modifyComment(@Valid CommentForm commentForm, BindingResult bindingResult,
                                @PathVariable("id") Long id, Principal principal){
        if(bindingResult.hasErrors()){
            return "comment_form";
        }

        Comment comment = commentService.getComment(id);
        if(!principal.getName().equals(comment.getAuthor().getUsername())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정권한이 없습니다.");
        }

        commentService.modify(comment, commentForm.getContent());
        return String.format("redirect:/post/detail/%s", comment.getPost().getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String deleteComment(Principal principal, @PathVariable("id") Long id){

        Comment comment = commentService.getComment(id);
        if(!principal.getName().equals(comment.getAuthor().getUsername())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"삭제권한이 없습니다.");
        }

        commentService.delete(comment);
        return String.format("redirect:/post/detail/%s", comment.getPost().getId());
    }
}
