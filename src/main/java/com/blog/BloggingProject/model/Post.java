package com.blog.BloggingProject.model;

import jakarta.persistence.*;

@Entity
@Table(name="post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="Title")
    private String title;
    @Lob
    @Column(columnDefinition = "TEXT",name="Content")
    private String content;
    @Column(name="Author")
    private String author;
    @Column(name="Access")
    private String visibility;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getVisibility(){
        return visibility;
    }
    public void setVisibility(String visibility){
        this.visibility=visibility;
    }
    public Post(){

    }
    public Post(int id, String title, String content, String author) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }
}
