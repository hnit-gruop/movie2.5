package com.yc.bean;

import java.sql.Timestamp;

public class Comments {
    private Integer commentsId;

    private Integer userId;

    private String comments;

    private Integer movieId;

    private Timestamp commentsTime;

    private Integer score;

    private User user;
    
    //点赞数
    private int agreeCnt;
    
    //详细时间描述
    private String tipTime;
    
    //是否赞过
    private boolean agree;
    

	public int getAgreeCnt() {
		return agreeCnt;
	}

	public void setAgreeCnt(int agreeCnt) {
		this.agreeCnt = agreeCnt;
	}

	public boolean isAgree() {
		return agree;
	}

	public void setAgree(boolean agree) {
		this.agree = agree;
	}

	public String getTipTime() {
		return tipTime;
	}

	public void setTipTime(String tipTime) {
		this.tipTime = tipTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	@Override
	public String toString() {
		return "Comments [commentsId=" + commentsId + ", userId=" + userId + ", comments=" + comments + ", movieId="
				+ movieId + ", commentsTime=" + commentsTime + ", score=" + score + ", user=" + user + ", agreeCnt="
				+ agreeCnt + ", tipTime=" + tipTime + ", agree=" + agree + "]";
	}
    
}