package com.dongkyoo.gongzza.vos;

import java.util.Objects;

public class HashTag {

    private int id;
    private int postId;
    private String color;
    private String title;

    public HashTag() {
    }

    public HashTag(String color, String title) {
        this.color = color;
        this.title = title;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getColor() {
        return color;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HashTag)) return false;
        HashTag hashTag = (HashTag) o;
        return color.equals(hashTag.color) &&
                title.equals(hashTag.title) &&
                id == hashTag.id &&
                postId == hashTag.postId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, title, id, postId);
    }

    @Override
    public String toString() {
        return "HashTag{" +
                "color='" + color + '\'' +
                ", title='" + title + '\'' +
                ", id='" + id + '\'' +
                ", postId='" + postId + '\'' +
                '}';
    }
}
