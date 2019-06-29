package com.yc.service;

import java.util.List;


import javax.servlet.http.HttpSession;

import com.yc.bean.CommentAgreeCnt;
import com.yc.bean.CommentAgreeUser;
import com.yc.bean.Comments;
import com.yc.bean.User;

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
	 * @param user
	 * @param commonts
	 */
    void setIfAgree(User user,List<Comments> commonts);
	
    
    void setAgreeCnt(List<Comments> list);
    
    void setAgreeCnt(Comments c);
    
    void updateCommentCnt(int commentsId,int i);
    
    
    void setRedisData(List<Comments> list);
    
    void setRedisData(Comments c);

	void updateCommentAgreeUser(int cid, int uid, int agree);

	void setIfAgree(User user, Comments c);
    
	//进入页面
	
	//判断当前用户是否点赞过 显示不同颜色
	
	//点击按钮
	
	//赞/取消
}
