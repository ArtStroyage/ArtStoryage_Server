package com.example.artstoryage.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.artstoryage.domain.Post;
import com.example.artstoryage.domain.enums.PostCategory;

public interface PostRepository extends JpaRepository<Post, Long> {
  Optional<Post> findByPostCategory(PostCategory postCategory);
}
