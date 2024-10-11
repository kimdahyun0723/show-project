package com.keduit.show.controller;


import com.keduit.show.entity.Comment;
import com.keduit.show.entity.Post;
import com.keduit.show.repository.CommentRepository;
import com.keduit.show.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/comments")
public class CommentController {

  @Autowired
  private CommentRepository commentRepository;

  @Autowired
  private PostRepository postRepository;

  // 댓글 작성
  @PostMapping("/add/{postId}")
  public String addComment(@PathVariable Long postId, @RequestParam String content, Model model) {
    Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));

    Comment comment = new Comment();
    comment.setContent(content);
    comment.setPost(post);

    commentRepository.save(comment);

    return "redirect:/posts/" + postId;
  }

  // 댓글 삭제
  @PostMapping("/delete/{commentId}")
  public String deleteComment(@PathVariable Long commentId, Model model) {
    Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("Comment not found"));
    Long postId = comment.getPost().getNum();

    commentRepository.delete(comment);

    return "redirect:/posts/" + postId;
  }
}
