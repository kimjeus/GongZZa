package com.dongkyoo.gongzza.vos;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.Objects;

/**
 * 작성자 : 이동규
 * 채팅 데이터 클래스
 */
@Entity
public class ChatLog implements Comparable<ChatLog>, Parcelable {

    @PrimaryKey
    private int id;

    @ColumnInfo(name = "post_id")
    private int postId;

    @ColumnInfo(name = "sender_id")
    private String senderId;

    @ColumnInfo(name = "sender_name")
    private String senderName;

    @ColumnInfo
    private String content;

    @ColumnInfo(name = "sent_at")
    private Date sentAt;

    public ChatLog() {
    }

    @Ignore
    public ChatLog(int id, int postId, String senderId, String content, Date sentAt) {
        this.id = id;
        this.postId = postId;
        this.senderId = senderId;
        this.content = content;
        this.sentAt = sentAt;
    }

    @Ignore
    public ChatLog(ChatLog chatLog) {
        this(chatLog.id, chatLog.postId, chatLog.senderId, chatLog.content, chatLog.sentAt);
    }

    @Ignore
    public ChatLog(Parcel parcel) {
        this.id = parcel.readInt();
        this.postId = parcel.readInt();
        this.senderId = parcel.readString();
        this.senderName = parcel.readString();
        this.content = parcel.readString();
        this.sentAt = (Date) parcel.readSerializable();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSentAt() {
        return sentAt;
    }

    public void setSentAt(Date sentAt) {
        this.sentAt = sentAt;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    @Override
    public int compareTo(ChatLog o) {
        return (int) (sentAt.getTime() - o.getSentAt().getTime());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(postId);
        dest.writeString(senderId);
        dest.writeString(senderName);
        dest.writeString(content);
        dest.writeSerializable(sentAt);
    }

    public static final Creator<ChatLog> CREATOR = new Creator<ChatLog>() {
        @Override
        public ChatLog createFromParcel(Parcel source) {
            return new ChatLog(source);
        }

        @Override
        public ChatLog[] newArray(int size) {
            return new ChatLog[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof ChatLog)) return false;
        ChatLog chatLog = (ChatLog) o;
        return (id > 0 && id == chatLog.id) ||
                (postId == chatLog.postId &&
                Objects.equals(senderId, chatLog.senderId) &&
                Objects.equals(senderName, chatLog.senderName) &&
                Objects.equals(content, chatLog.content) &&
                Objects.equals(sentAt, chatLog.sentAt));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, postId, senderId, senderName, content, sentAt);
    }
}
