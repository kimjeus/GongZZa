package com.dongkyoo.gongzza.vos;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.dongkyoo.gongzza.cache.DateConverter;

import java.util.Date;
import java.util.Objects;

@Entity
@TypeConverters(value = {DateConverter.class})
public class Post implements Parcelable {

    @PrimaryKey
    private int id;

    @ColumnInfo(name = "user_id")
    private String userId;

    @ColumnInfo
    private String title;

    @ColumnInfo
    private String content;

    @ColumnInfo(name = "title_image_url")
    private String titleImageUrl;

    @ColumnInfo(name = "total_num_participants")
    private int totalNumParticipants;

    @ColumnInfo(name = "meet_date_time")
    private Date meetDateTime;

    @ColumnInfo(name = "created_at")
    private Date createdAt;

    public Post() {
    }

    @Ignore
    public Post(Post post) {
        this.id = post.id;
        this.userId = post.userId;
        this.title = post.title;
        this.content = post.content;
        this.titleImageUrl = post.titleImageUrl;
        this.totalNumParticipants = post.totalNumParticipants;
        this.meetDateTime = post.meetDateTime;
        this.createdAt = post.createdAt;
    }

    @Ignore
    public Post(Parcel parcel) {
        id = parcel.readInt();
        userId = parcel.readString();
        title = parcel.readString();
        content = parcel.readString();
        titleImageUrl = parcel.readString();
        totalNumParticipants = parcel.readInt();
        meetDateTime = (Date) parcel.readSerializable();
        createdAt = (Date) parcel.readSerializable();
    }

    @Ignore
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

    @Ignore
    public Post(String userId, String title, String content, Date meetDateTime, int totalNumParticipants, String titleImageUrl) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.meetDateTime = meetDateTime;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(userId);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(titleImageUrl);
        dest.writeInt(totalNumParticipants);
        dest.writeSerializable(meetDateTime);
        dest.writeSerializable(createdAt);
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel source) {
            return new Post(source);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };
}
