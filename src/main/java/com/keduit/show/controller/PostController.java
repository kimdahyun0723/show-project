package com.keduit.show.controller;

import com.keduit.show.dto.PostDTO;
import com.keduit.show.dto.PostSearchDTO;
import com.keduit.show.entity.Post;
import com.keduit.show.repository.PostRepository;
import com.keduit.show.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("posts")
public class PostController {

  @Autowired
  private PostRepository postRepository;

  @Autowired
  private PostService postService;

  // 게시글 목록 조회
  @GetMapping({"/list","/list/{page}"})
  public String getAllPosts(PostSearchDTO postSearchDTO, @PathVariable("page") Optional<Integer> page, Model model, Principal principal) {
    Pageable pageable = PageRequest.of(page.orElse(0), 5);
    Page<Post> posts = postService.getPostPage(postSearchDTO, pageable);

    model.addAttribute("maxPage", 10);
    model.addAttribute("posts", posts);
    model.addAttribute("postSearchDTO", postSearchDTO);
    model.addAttribute("memberId", principal == null ? "login" : principal.getName());
    return "/post/index";
  }

  // 게시글 작성 폼 이동
  @GetMapping("/new")
  public String showCreateForm(Model model, Principal principal) {
    model.addAttribute("memberId", principal.getName());
    PostDTO postDTO = new PostDTO();
    model.addAttribute("postDTO", postDTO);

    return "/post/create";
  }

  // 게시글 생성
  @PostMapping
  public String createPost(PostDTO postDTO, Principal principal) {
    postService.createPost(postDTO, principal.getName());
    return "redirect:/posts/list";
  }

  // 게시글 상세보기
  @GetMapping("/{id}")
  public String getPostById(@PathVariable Long id, Model model) {
    postService.getPost(id);
    Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
    model.addAttribute("post", post);
    return "/post/detail";
  }

  // 게시글 수정 폼 이동
  @GetMapping("/edit/{id}")
  public String showUpdateForm(@PathVariable Long id, Model model) {
    Post post = postService.getPost(id);
    model.addAttribute("post", post);
    return "/post/edit";
  }

  // 게시글 수정 처리
  @PostMapping("/{id}")
  public String updatePost(@PathVariable Long id, Post postDetails) {
    postService.updatePost(id, postDetails);
    return "redirect:/posts";
  }

  // 게시글 삭제
  @PostMapping("/{id}/delete")
  public String deletePost(@PathVariable Long id) {
    postService.deletePost(id);
    return "redirect:/posts";
  }
}
