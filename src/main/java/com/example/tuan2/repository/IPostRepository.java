package com.example.tuan2.repository;


import com.example.tuan2.model.Post;
import com.example.tuan2.model.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByTitleContaining(Pageable pageable, String title);
    Iterable <Post> findAllByType(Type type);}
