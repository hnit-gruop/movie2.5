package com.yc.bean;

public class Score {
    private Integer id;

    private Integer movieId;

    private Double sumScore;

    private Integer sumPeople;
    
    public Score() {
    	
    }
    
    public Score(Integer movieId, Double sumScore, Integer sumPeople, Double score, int width) {
		this.movieId = movieId;
		this.sumScore = sumScore;
		this.sumPeople = sumPeople;
		this.score = score;
		this.width = width;
	}

	//评分
    private Double score;
    
    private int width;

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public Double getSumScore() {
        return sumScore;
    }

    public void setSumScore(Double sumScore) {
        this.sumScore = sumScore;
    }

    public Integer getSumPeople() {
        return sumPeople;
    }

    public void setSumPeople(Integer sumPeople) {
        this.sumPeople = sumPeople;
    }
}