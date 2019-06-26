package com.yc.service;

import java.util.List;


import javax.servlet.http.HttpSession;

import com.yc.bean.CommentAgreeCnt;
import com.yc.bean.CommentAgreeUser;
import com.yc.bean.Comments;

public interface CommentService {
	
	List<CommentAgreeUser> getCommentsUserList();
	
	List<CommentAgreeCnt> getCommentsAgreeCnt();
	
	List<Comments>getCommonts(int movieId);
	
	Comments get(int CommnetsId);
	
	void setUser(List<Comments> list);
	
	void setUser(Comments c);
	
	int addComment(Comments comments);
	
	int agree(CommentAgreeUser commentAgreeUser);
	
	int concelAgree(CommentAgreeUser commentAgreeUser);
	
	
	/**
	 * 判断是否赞过
	 * @param session
	 * @param c
	 */
    void setIfAgree(HttpSession session,Comments c);
	
    void setIfAgree(HttpSession session,List<Comments> list);
    
    void setAgreeCnt(List<Comments> list);
    
    void setAgreeCnt(Comments c);
    
    void updateCommentCnt(int commentsId,int i);
    
    
    void setRedisData(List<Comments> list);
    
    void setRedisData(Comments c);

	void updateCommentAgreeUser(int cid, int uid, int agree);
    
	//进入页面
	
	//判断当前用户是否点赞过 显示不同颜色
	
	//点击按钮
	
	//赞/取消
}
