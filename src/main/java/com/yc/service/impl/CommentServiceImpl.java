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
import com.yc.service.RedisService;
import com.yc.service.UserService;
import com.yc.util.DateUtils;

@Service
public class CommentServiceImpl implements com.yc.service.CommentService{
	
	@Autowired
	RedisService redisService;
	
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
		List<Comments> listComments = commentsMapper.listComments(movieId);
		setRedisData(listComments);
		return listComments;
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
		
		if(user!=null) {
			boolean ifAgree = redisService.ifAgree(user.getUserId(), c.getCommentsId());
			c.setAgree(ifAgree);
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
		return redisService.addCommentsCnt(commentAgreeUser);
	}

	/**
	 * 取消
	 */
	@Override
	public int concelAgree(CommentAgreeUser commentAgreeUser) {
		return redisService.deCommentsCnt(commentAgreeUser);
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
		CommentAgreeCnt record = new CommentAgreeCnt();
		record.setAgreeCnt(i);
		record.setCommentid(commentsId);
		CommentAgreeCntExample example = new CommentAgreeCntExample();
		example.createCriteria().andCommentidEqualTo(commentsId);
		commentAgreeCntMapper.updateByExampleSelective(record, example);
	}

	@Override
	public void setRedisData(List<Comments> list) {
		for (Comments c : list) {
			setRedisData(c);
			c.setTipTime(DateUtils.convertTimeToFormat(c.getCommentsTime().getTime()));
		}
	}

	@Override
	public void setRedisData(Comments c) {
		Integer userId = c.getUserId();
		Integer commentsId = c.getCommentsId();
		
		int commentAgreeCnt = redisService.getCommentAgreeCnt(commentsId);
		c.setAgreeCnt(commentAgreeCnt);
	
	}

	@Override
	public List<CommentAgreeUser> getCommentsUserList() {
		return commentAgreeUserMapper.selectByExample(null);
	}

	@Override
	public List<CommentAgreeCnt> getCommentsAgreeCnt() {
		return commentAgreeCntMapper.selectByExample(null);
	}

	@Override
	public void updateCommentAgreeUser(int cid, int uid, int agree) {
		CommentAgreeUser commentAgreeUser = new CommentAgreeUser();
		commentAgreeUser.setCommentsId(cid);
		commentAgreeUser.setUserId(uid);
		commentAgreeUser.setIfagree(agree);
		CommentAgreeUserExample example = new CommentAgreeUserExample();
		example.createCriteria().andCommentsIdEqualTo(cid).andUserIdEqualTo(uid);
		int update = commentAgreeUserMapper.updateByExampleSelective(commentAgreeUser, example);
		//如果没有更新成功，可能是用户没来点过赞，表中没这条数据
		if(update==0) {
			commentAgreeUserMapper.insertSelective(commentAgreeUser);
			
		}
		
	}
}
