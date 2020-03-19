package my.blog.myblog.entity;

import java.util.Date;

public class Article {
    private Integer id;
    private String title;
    private String main;
    private String date;
    private String author;
    private Integer poke;

    public Integer getPoke() {
        return poke;
    }

    public void setPoke(Integer poke) {
        this.poke = poke;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
