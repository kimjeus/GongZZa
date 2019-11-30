package com.dongkyoo.gongzza.dtos;

import com.dongkyoo.gongzza.vos.HashTag;
import com.dongkyoo.gongzza.vos.Participant;
import com.dongkyoo.gongzza.vos.Post;
import com.dongkyoo.gongzza.vos.User;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class PostDto extends Post {

    private List<Participant> participantList;
    private List<HashTag> hashTagList;

    public PostDto() {
    }

    public PostDto(int id, String userId, String title, String content, Date meetDateTime, Date createdAt, int totalNumOfParticipants, String titleImageUrl, List<Participant> participantList, List<HashTag> hashTagList) {
        super(id, userId, title, content, meetDateTime, createdAt, totalNumOfParticipants, titleImageUrl);
        this.participantList = participantList;
        this.hashTagList = hashTagList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PostDto)) return false;
        if (!super.equals(o)) return false;
        PostDto postDto = (PostDto) o;
        return Objects.equals(participantList, postDto.participantList) &&
                Objects.equals(hashTagList, postDto.hashTagList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), participantList, hashTagList);
    }

    @Override
    public String toString() {
        return "PostDto{" +
                "participantList=" + participantList +
                ", hashTagList=" + hashTagList +
                '}';
    }

    public List<Participant> getParticipantList() {
        return participantList;
    }

    public void setParticipantList(List<Participant> participantList) {
        this.participantList = participantList;
    }

    public List<HashTag> getHashTagList() {
        return hashTagList;
    }

    public void setHashTagList(List<HashTag> hashTagList) {
        this.hashTagList = hashTagList;
    }
}
