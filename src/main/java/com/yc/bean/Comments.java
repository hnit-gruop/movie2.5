package com.yc.bean;

import java.sql.Timestamp;

public class Comments {
    private Integer commentsId;

    private Integer userId;

    private String comments;

    private Integer movieId;

    private Timestamp commentsTime;

    private Integer score;

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

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments == null ? null : comments.trim();
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public Timestamp getCommentsTime() {
        return commentsTime;
    }

    public void setCommentsTime(Timestamp commentsTime) {
        this.commentsTime = commentsTime;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

	public void setTipTime(String convertTimeToFormat) {
		// TODO Auto-generated method stub
		
	}

	public void setUser(User user) {
		// TODO Auto-generated method stub
		
	}

	public void setAgree(boolean ifAgree) {
		// TODO Auto-generated method stub
		
	}

	public void setAgreeCnt(int ac) {
		// TODO Auto-generated method stub
		
	}
}