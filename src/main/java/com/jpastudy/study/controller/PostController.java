package com.jpastudy.study.controller;

import com.jpastudy.study.domain.CommentForm;
import com.jpastudy.study.domain.Member;
import com.jpastudy.study.domain.Post;
import com.jpastudy.study.domain.PostForm;
import com.jpastudy.study.service.MemberService;
import com.jpastudy.study.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/post")
@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final MemberService memberService;

    @GetMapping("/list")
    public String list(Model model,
                       @RequestParam(value = "page", defaultValue = "0") int page){
        Page<Post> paging = postService.getList(page);
        model.addAttribute("paging", paging);
        return "post/list";
    }

    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Long id, CommentForm commentForm){
        Post post = postService.getPost(id);
        model.addAttribute("post", post);
        return "post/detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/new")
    public String createForm(Model model){
        model.addAttribute("postForm", new PostForm());
        return "post/createPostForm";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/new")
    public String create(@Valid PostForm form, BindingResult bindingResult,
                         Principal principal){
        if(bindingResult.hasErrors()){
            return "post/createPostForm";
        }

        Member member = memberService.getMember(principal.getName());

        postService.create(form.getSubject(), form.getContent(), member);
        return "redirect:/post/list";
    }
}
