package com.example.bloodbankapp.data.model;

public class ModelPostRecycler {

   private Integer id ;
    private String title,content,thumbnail_full_path;
    private  boolean is_favourite;

    public ModelPostRecycler(Integer id, String title, String content, String thumbnail_full_path, boolean is_favourite) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.thumbnail_full_path = thumbnail_full_path;
        this.is_favourite = is_favourite;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getThumbnail_full_path() {
        return thumbnail_full_path;
    }

    public void setThumbnail_full_path(String thumbnail_full_path) {
        this.thumbnail_full_path = thumbnail_full_path;
    }

    public boolean isIs_favourite() {
        return is_favourite;
    }

    public void setIs_favourite(boolean is_favourite) {
        this.is_favourite = is_favourite;
    }
}
