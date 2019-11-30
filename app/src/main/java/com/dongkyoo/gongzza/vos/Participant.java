package com.dongkyoo.gongzza.vos;

public class Participant {

    private int postId;
    private User user;

    public Participant() {
    }

    public Participant(int postId, User user) {
        this.postId = postId;
        this.user = user;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
