package com.example.tuan2.model;

import org.springframework.web.multipart.MultipartFile;

public class PostForm {
    private Long id;
    private String title;
    private String content;
    private String description;
    private MultipartFile image; // Chuyển đổi trường image sang MultipartFile
    private Type type;

    public PostForm() {
    }

    public PostForm(Long id, String title, String content, String description, MultipartFile image, Type type) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.description = description;
        this.image = image;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
