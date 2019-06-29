package com.yc.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.yc.bean.CommentAgreeCnt;
import com.yc.bean.CommentAgreeUser;
import com.yc.bean.Score;
import com.yc.service.CommentService;
import com.yc.service.RedisService;
import com.yc.service.ScoreService;

@Service
public class RedisServiceImpl implements RedisService {
	
	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private ScoreService scoreService;
	
	@Autowired
	private CommentService commentService;

	@Override
	public void init() {
		
		//电影评分数据
		List<Score> all = scoreService.getAll();
		for (Score score : all) {
			Integer sum = (int) score.getSumScore().floatValue();
			Integer cnt = score.getSumPeople();
			String s = String.valueOf(sum);
			String c = String.valueOf(cnt);
			StringBuilder sb = new StringBuilder();
			sb.append(s).append(":").append(c);
			redisTemplate.opsForHash().put("ms", String.valueOf(score.getMovieId()), sb);
		}
		
		//帖子点赞数量
		List<CommentAgreeCnt> commentsCnt = commentService.getCommentsAgreeCnt();
		for (CommentAgreeCnt c : commentsCnt) {
			redisTemplate.opsForHash().put("comment_agree_cnt", ""+c.getCommentid(), c.getAgreeCnt()+"");
		}
		
		//用户点赞表
		List<CommentAgreeUser> commentsUserList = commentService.getCommentsUserList();
		for (CommentAgreeUser u : commentsUserList) {
			redisTemplate.opsForHash().put("comment_agree_user", u.getCommentsId()+":"+u.getUserId(), u.getIfagree());
		}
	}
	
	/**
	 * 综合评分
	 */
	@Override
	public double getAvgScore(int id) {
		Object object = redisTemplate.opsForHash().get("ms", ""+id);
		
		if(object!=null) {
			String string = object.toString();
			String[] split = string.split(":");
			if(split.length>1) {
				String sum = split[0];
				String cnt = split[1];
				double s = Double.parseDouble(sum);
				int c = Integer.parseInt(cnt);
				
				Double d = s/c;
				String format = String.format("%.1f", d);
				return new Double(format);
			}
		}
		return 8.0;
	}

	@Override
	public int getCommentAgreeCnt(int commentsId) {
		Object object = redisTemplate.opsForHash().get("comment_agree_cnt", commentsId+"");
		if(object!=null) {
			String string = object.toString();
			return Integer.parseInt(string);
		}
		return 0;
	}

	@Override
	public boolean  ifAgree(int userId, int commentsId) {
		Object object = redisTemplate.opsForHash().get("comment_agree_user", commentsId+":"+userId);
		if(object!=null)
			return Integer.parseInt(object.toString())>0?true:false;
		return false;
	}

	@Override
	public int addCommentsCnt(CommentAgreeUser c) {
		try {
			int old = getCommentAgreeCnt(c.getCommentsId());
			//评价点赞数+1
			redisTemplate.opsForHash().put("comment_agree_cnt", c.getCommentsId()+"", ""+(old+1));
			//用户点赞状态更新
			redisTemplate.opsForHash().put("comment_agree_user", c.getCommentsId()+":"+c.getUserId(), "1");
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public int deCommentsCnt(CommentAgreeUser c) {
		try {
			int old = getCommentAgreeCnt(c.getCommentsId());
			//评价点赞数-1
			redisTemplate.opsForHash().put("comment_agree_cnt", c.getCommentsId()+"", ""+(old-1));
			//用户点赞状态更新
			redisTemplate.opsForHash().put("comment_agree_user", c.getCommentsId()+":"+c.getUserId(), "0");
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public void flushToMysql() {
		//电影评分
		 Map<String,Object> entries = redisTemplate.opsForHash().entries("ms");
		 for (Map.Entry<String, Object> m :  entries.entrySet()) {
			int movieId = Integer.parseInt(m.getKey());
			String str = m.getValue().toString();
			String[] split = str.split(":");
			if(split.length>1) {
				String sum = split[0];
				String cnt = split[1];
				double s = Double.parseDouble(sum);
				int c = Integer.parseInt(cnt);
				Score score = new Score();
				score.setMovieId(movieId);
				score.setSumScore(s);
				score.setSumPeople(c);
				scoreService.updateScore(score);
			}
		 }
		 
		//评论赞数
		 Map<String,Object> et2 = redisTemplate.opsForHash().entries("comment_agree_cnt");
		 for (Map.Entry<String, Object> m : et2.entrySet()) {
			int  commentid = Integer.parseInt(m.getKey());
			int cnt = Integer.parseInt(m.getValue().toString());
			commentService.updateCommentCnt(commentid, cnt);
		 }
		
		//用户点赞
		 Map<String,Object> et3 = redisTemplate.opsForHash().entries("comment_agree_user");
		 for (Map.Entry<String, Object> m : et3.entrySet()) {
			int agree = Integer.parseInt(m.getValue().toString());
			
			String string = m.getKey().toString();
			String[] split = string.split(":");
			if(split.length>1) {
				String commentIdStr = split[0];
				String UserIdStr = split[1];
				
				int cid = Integer.parseInt(commentIdStr);
				int uid = Integer.parseInt(UserIdStr);
				commentService.updateCommentAgreeUser(cid, uid,agree);
			}
		 }
	}

	@Override
	public Score getScore(int movieId) {
		Score score = new Score(movieId, 80.0,10, 8.0, 80);
		Object object = redisTemplate.opsForHash().get("ms", ""+movieId);
		if(object==null)
			return score;
		String string = object.toString();
		String[] split = string.split(":");
		if(split.length>1) {
			String sum = split[0];
			String cnt = split[1];
			double s = Double.parseDouble(sum);
			int c = Integer.parseInt(cnt);
			Double d = s/c;
			String format = String.format("%.1f", d);
			score.setScore(new Double(format));
			score.setSumPeople(c);
			score.setSumScore(s);
			int width = (int) (score.getScore()*10);
			score.setWidth(width);
		}
		return score;
	}
	
	
	/**
	 * 更新电影评分
	 */
	@Override
	public int updateScore(int movieId, int userScore) {
		Score score= getScore(movieId);
		Double sumScore = score.getSumScore();
		Integer sumPeople = score.getSumPeople();
		
		try {
			if(sumScore==null||sumPeople==null) {
				redisTemplate.opsForHash().put("ms", movieId+"", userScore+":"+1);
			}else {
				sumScore+=userScore;
				sumPeople+=1;
				redisTemplate.opsForHash().put("ms", movieId+"", sumScore+":"+sumPeople);
			}
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

}
