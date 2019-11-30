package com.dongkyoo.gongzza.vos;

import java.util.Date;
import java.util.Objects;

public class Post {

    private int id;
    private String userId;
    private String title;
    private String content;
    private String titleImageUrl;
    private int totalNumParticipants;
    private Date meetDateTime;
    private Date createdAt;

    public Post() {
    }

    public Post(int id, String userId, String title, String content, Date meetDateTime, Date createdAt, int totalNumParticipants, String titleImageUrl) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.meetDateTime = meetDateTime;
        this.createdAt = createdAt;
        this.totalNumParticipants = totalNumParticipants;
        this.titleImageUrl = titleImageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Date getMeetDateTime() {
        return meetDateTime;
    }

    public void setMeetDateTime(Date meetDateTime) {
        this.meetDateTime = meetDateTime;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getTotalNumParticipants() {
        return totalNumParticipants;
    }

    public void setTotalNumParticipants(int totalNumParticipants) {
        this.totalNumParticipants = totalNumParticipants;
    }

    public String getTitleImageUrl() {
        return titleImageUrl;
    }

    public void setTitleImageUrl(String titleImageUrl) {
        this.titleImageUrl = titleImageUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Post)) return false;
        Post post = (Post) o;
        return id == post.id &&
                userId.equals(post.userId) &&
                totalNumParticipants == post.totalNumParticipants &&
                Objects.equals(title, post.title) &&
                Objects.equals(content, post.content) &&
                Objects.equals(meetDateTime, post.meetDateTime) &&
                Objects.equals(createdAt, post.createdAt) &&
                Objects.equals(titleImageUrl, post.titleImageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, title, content, meetDateTime, createdAt, totalNumParticipants, titleImageUrl);
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", meetDateTime=" + meetDateTime +
                ", createdAt=" + createdAt +
                ", totalNumParticipants=" + totalNumParticipants +
                ", titleImageUrl='" + titleImageUrl + '\'' +
                '}';
    }
}
