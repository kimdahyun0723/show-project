package com.keduit.show.controller;

import com.keduit.show.entity.Post;
import com.keduit.show.repository.PostRepository;
import com.keduit.show.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostController {

  @Autowired
  private PostRepository postRepository;

  @Autowired
  private PostService postService;

  // 게시글 목록 조회
  @GetMapping
  public String getAllPosts(Model model) {

    List<Post> posts = postService.getAll();
    model.addAttribute("posts", posts);
    return "/post/index";
  }

  // 게시글 작성 폼 이동
  @GetMapping("/new")
  public String showCreateForm(Post post) {
    return "/post/create";
  }

  // 게시글 생성
  @PostMapping
  public String createPost(Post post) {
    postService.createPost(post);
    return "redirect:/posts";
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
