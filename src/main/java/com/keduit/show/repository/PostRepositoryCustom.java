package com.keduit.show.repository;

import com.keduit.show.dto.PostSearchDTO;
import com.keduit.show.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {

    Page<Post> getBoardsPage(PostSearchDTO postSearchDTO, Pageable pageable);
}
