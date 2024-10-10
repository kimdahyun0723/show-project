package com.keduit.show.repository;

import com.keduit.show.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<com.keduit.show.entity.Post, Long> {
}
