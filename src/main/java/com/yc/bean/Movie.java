package com.yc.bean;

import java.util.Date;
import java.util.List;


public class Movie {
    private Integer movieId;

    private String name;

    private Integer duration;

    private Date releaseTime;

    private String foreignName;

    private String region;

    private String language;

    private String description;

    private String status;
    
    private MovieImage movieImage;
    
    
    
    public MovieImage getMovieImage() {
		return movieImage;
	}

	public void setMovieImage(MovieImage movieImage) {
		this.movieImage = movieImage;
	}

	/**
     * 非数据库字段
     */
    private String coverImage;
    private Double score;
    
    
    
    private List<MovieType> type;
    

    public List<MovieType> getType() {
		return type;
	}

	public void setType(List<MovieType> type) {
		this.type = type;
	}
    
    public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public String getCoverImage() {
		return coverImage;
	}

	public void setCoverImage(String coverImage) {
		this.coverImage = coverImage;
	}

	public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Date getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Date releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getForeignName() {
        return foreignName;
    }

    public void setForeignName(String foreignName) {
        this.foreignName = foreignName == null ? null : foreignName.trim();
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region == null ? null : region.trim();
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language == null ? null : language.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

}