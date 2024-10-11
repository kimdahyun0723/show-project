package com.keduit.show.service;

import com.keduit.show.dto.PostDTO;
import com.keduit.show.dto.PostSearchDTO;
import com.keduit.show.entity.Member;
import com.keduit.show.entity.Post;
import com.keduit.show.repository.MemberRepository;
import com.keduit.show.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final MemberRepository memberRepository;


    public void createPost(PostDTO postDTO, String memberId) {
        Post post = Post.createPost(postDTO);

        Member member = memberRepository.findById(memberId);
        post.setMember(member);
        postRepository.save(post);
    }

    public Post getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));

        return post;

    }


    public void updatePost(Long id, Post postDetails) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        post.setTitle(postDetails.getTitle());
        post.setContent(postDetails.getContent());
        postRepository.save(post);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    public Page<Post> getPostPage(PostSearchDTO postSearchDTO, Pageable pageable) {
        return postRepository.getBoardsPage(postSearchDTO, pageable);
    }
}
