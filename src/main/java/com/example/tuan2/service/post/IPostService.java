package com.example.tuan2.service.post;

import com.example.tuan2.model.Post;
import com.example.tuan2.model.Type;
import com.example.tuan2.service.IGenerateService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPostService extends IGenerateService<Post> {
    Page<Post> findAllByTitleContaining(Pageable pageable, String title);
    Iterable <Post> findAllByType(Type type);
}



