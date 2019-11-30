package com.dongkyoo.gongzza.dtos;

import android.os.Parcel;
import android.os.Parcelable;

import com.dongkyoo.gongzza.vos.HashTag;
import com.dongkyoo.gongzza.vos.Participant;
import com.dongkyoo.gongzza.vos.Post;
import com.dongkyoo.gongzza.vos.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class PostDto extends Post implements Parcelable {

    private List<Participant> participantList;
    private List<HashTag> hashTagList;

    public PostDto() {
    }

    public PostDto(PostDto postDto) {
        super(postDto);

        this.participantList = postDto.participantList;
        this.hashTagList = postDto.hashTagList;
    }

    public PostDto(Post post) {
        super(post);

        this.participantList = new ArrayList<>();
        this.hashTagList = new ArrayList<>();
    }

    public PostDto(Parcel parcel) {
        super(parcel);

        participantList = new ArrayList<>();
        parcel.readTypedList(participantList, Participant.CREATOR);

        hashTagList = new ArrayList<>();
        parcel.readTypedList(hashTagList, HashTag.CREATOR);
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

    /**
     * 유저가 멤버인지 확인해주는 메소드
     * @param user
     * @return
     */
    public boolean isMember(User user) {
        return participantList.contains(new Participant(getId(), user));
    }

    /**
     * 유저를 멤버에서 탈퇴시켜주는 메소드
     * @param user
     */
    public void leave(User user) {
        participantList.remove(new Participant(getId(), user));
    }

    /**
     * 멤버 추가해주는 메소드
     * @param participant
     */
    public void enroll(Participant participant) {
        participantList.add(participant);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(participantList);
        dest.writeTypedList(hashTagList);
    }

    public static final Creator<PostDto> CREATOR = new Creator<PostDto>() {
        @Override
        public PostDto createFromParcel(Parcel source) {
            return new PostDto(source);
        }

        @Override
        public PostDto[] newArray(int size) {
            return new PostDto[size];
        }
    };
}
