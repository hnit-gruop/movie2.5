package com.yc.service.impl;

import java.sql.Timestamp;



import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yc.bean.CommentAgreeCnt;
import com.yc.bean.CommentAgreeCntExample;
import com.yc.bean.CommentAgreeUser;
import com.yc.bean.CommentAgreeUserExample;
import com.yc.bean.Comments;
import com.yc.bean.CommentsExample;
import com.yc.bean.User;
import com.yc.dao.CommentAgreeCntMapper;
import com.yc.dao.CommentAgreeUserMapper;
import com.yc.dao.CommentsMapper;
import com.yc.service.UserService;
import com.yc.util.DateUtils;


@Service
public class CommentServiceImpl implements com.yc.service.CommentService{
	
	@Autowired
	CommentsMapper commentsMapper;
	
	@Autowired
	UserService userService;
	
	@Autowired
	CommentAgreeUserMapper commentAgreeUserMapper;
	
	@Autowired
	CommentAgreeCntMapper commentAgreeCntMapper;
	
	
	@Override
	public List<Comments> getCommonts(int movieId) {
		CommentsExample example = new CommentsExample();
		example.createCriteria().andMovieIdEqualTo(movieId);
		example.setOrderByClause("comments_id desc");
		List<Comments> list = commentsMapper.selectByExample(example);
		setUser(list);
		setAgreeCnt(list);
		return list;
	}

	@Override
	public void setUser(List<Comments> list) {
		for (Comments comments : list) {
			setUser(comments);
		}
	}
	
	@Override
	public void setUser(Comments c) {
		int userId = c.getUserId();
		User user = userService.get(userId);
		c.setTipTime(DateUtils.convertTimeToFormat(c.getCommentsTime().getTime()));
		c.setUser(user);
		
	}

	@Override
	public int addComment(Comments comments) {
		Timestamp timestamp = new Timestamp(new Date().getTime());
		comments.setCommentsTime(timestamp);
		return 	commentsMapper.insertSelective(comments);
	}





	/**
	 * 用户是否赞过这条评论
	 */
	@Override
	public void setIfAgree(HttpSession session, Comments c) {
		Object u = session.getAttribute("user");
		if(u==null)
			return;
		User user = (User) u;
		
		CommentAgreeUserExample example = new CommentAgreeUserExample();
		example.createCriteria().andUserIdEqualTo(user.getUserId()).andCommentsIdEqualTo(c.getCommentsId());
		List<CommentAgreeUser> list = commentAgreeUserMapper.selectByExample(example);
		if(list.size()>0) {
			if(list.get(0).getIfagree()>0)
			c.setAgree(true);
		}
	}

	@Override
	public void setIfAgree(HttpSession session, List<Comments> list) {
		for (Comments c : list) {
			setIfAgree(session,c);
		}
	}

	
	@Override
	public void setAgreeCnt(List<Comments> list) {
		for (Comments c : list) {
			setAgreeCnt(c);
		}
	}
	
	/**
	 * 评论点赞数
	 */
	@Override
	public void setAgreeCnt(Comments c) {
		CommentAgreeCntExample example = new CommentAgreeCntExample();
		example.createCriteria().andCommentidEqualTo(c.getCommentsId());
		List<CommentAgreeCnt> list = commentAgreeCntMapper.selectByExample(example);
		if(list.size()>0) {
			int ac = list.get(0).getAgreeCnt();
			c.setAgreeCnt(ac);
		}
	}

	
	/**
	 * 点赞
	 */
	@Override
	public int agree(CommentAgreeUser commentAgreeUser) {
		updateCommentCnt(commentAgreeUser.getCommentsId(),1);
		
		commentAgreeUser.setIfagree(1);
		CommentAgreeUserExample example = new CommentAgreeUserExample();
		example.createCriteria().andCommentsIdEqualTo(commentAgreeUser.getCommentsId()).andUserIdEqualTo(commentAgreeUser.getUserId());
		return commentAgreeUserMapper.updateByExampleSelective(commentAgreeUser, example);
	}

	/**
	 * 取消
	 */
	@Override
	public int concelAgree(CommentAgreeUser commentAgreeUser) {
		updateCommentCnt(commentAgreeUser.getCommentsId(),-1);
		commentAgreeUser.setIfagree(0);
		CommentAgreeUserExample example = new CommentAgreeUserExample();
		example.createCriteria().andCommentsIdEqualTo(commentAgreeUser.getCommentsId()).andUserIdEqualTo(commentAgreeUser.getUserId());
		return commentAgreeUserMapper.updateByExampleSelective(commentAgreeUser, example);
	}


	@Override
	public Comments get(int commnetsId) {
		return commentsMapper.selectByPrimaryKey(commnetsId);
	}

	/**
	 * 更新赞数
	 */
	@Override
	public void updateCommentCnt(int commentsId, int i) {
		Comments comments = get(commentsId);
		comments.setAgreeCnt(comments.getAgreeCnt()+i);
		commentsMapper.updateByPrimaryKeySelective(comments);
	}
}
