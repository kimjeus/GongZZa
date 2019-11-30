package com.dongkyoo.gongzza.vos;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class CacheState {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo
    private Date postLastUpdateDatetime;

    @ColumnInfo
    private Date ChatLastUpdateDatetime;

    public CacheState() {
    }

    @Ignore
    public CacheState(Date postLastUpdateDatetime, Date chatLastUpdateDatetime) {
        this.postLastUpdateDatetime = postLastUpdateDatetime;
        ChatLastUpdateDatetime = chatLastUpdateDatetime;
    }

    public Date getPostLastUpdateDatetime() {
        return postLastUpdateDatetime;
    }

    public void setPostLastUpdateDatetime(Date postLastUpdateDatetime) {
        this.postLastUpdateDatetime = postLastUpdateDatetime;
    }

    public Date getChatLastUpdateDatetime() {
        return ChatLastUpdateDatetime;
    }

    public void setChatLastUpdateDatetime(Date chatLastUpdateDatetime) {
        ChatLastUpdateDatetime = chatLastUpdateDatetime;
    }

    public static CacheState createNow() {
        return new CacheState(
                new Date(System.currentTimeMillis()),
                new Date(System.currentTimeMillis())
        );
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
