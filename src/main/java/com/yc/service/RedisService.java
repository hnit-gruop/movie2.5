package com.yc.service;


import com.yc.bean.CommentAgreeUser;
import com.yc.bean.Comments;
import com.yc.bean.Score;

public interface RedisService {
	
	Score getScore(int movieId);
	
	int addCommentsCnt(CommentAgreeUser c);
	
	int deCommentsCnt(CommentAgreeUser c);
	/**
	 * 初始化reids
	 */
	void init();
	
	void flushToMysql();
	
	/**
	 * 电影综合评分
	 * @param id
	 * @return
	 */
	double getAvgScore(int id);
	
	/**
	 * 评论点赞数量
	 * @param commentsId
	 * @return
	 */
	int getCommentAgreeCnt(int commentsId);
	
	
	/**
	 * 用户是否点赞过这个评论
	 * @param userId
	 * @param commentsId
	 * @return
	 */
	boolean  ifAgree(int userId,int commentsId);
}
