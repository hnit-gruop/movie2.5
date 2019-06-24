package com.yc.bean;

public class CommentAgreeUser {
    private Integer id;

    private Integer commentsId;

    private Integer userId;

    private Integer ifagree;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCommentsId() {
        return commentsId;
    }

    public void setCommentsId(Integer commentsId) {
        this.commentsId = commentsId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getIfagree() {
        return ifagree;
    }

    public void setIfagree(Integer ifagree) {
        this.ifagree = ifagree;
    }
}