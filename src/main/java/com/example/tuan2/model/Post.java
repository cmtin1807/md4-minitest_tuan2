package com.example.tuan2.model;


import javax.persistence.*;

@Entity
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private String description;
    private String image;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private Type type;


    public Post() {}

    public Post(Long id, String title, String content, String description, String image, Type type) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.description = description;
        this.image = image;
        this.type = type;
    }

    public Post(Long id, String title, String content, String description, String image) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.description = description;
        this.image = image;
    }

    public Post(Long id, String title, String content, String description, Type type) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.description = description;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
