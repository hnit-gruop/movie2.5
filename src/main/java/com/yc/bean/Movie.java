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
    
    private List<MovieType> type;

    public List<MovieType> getType() {
		return type;
	}

	public void setType(List<MovieType> type) {
		this.type = type;
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

	public void setBigImage(String bigImage) {
		// TODO Auto-generated method stub
		
	}

	public void setListType(List<Type> listType) {
		// TODO Auto-generated method stub
		
	}

	public void setSmallImage(List<MovieImage> list) {
		// TODO Auto-generated method stub
		
	}

	public List<Type> getListType() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setCoverImage(String cover) {
		// TODO Auto-generated method stub
		
	}

	public void setScore(double avgScore) {
		// TODO Auto-generated method stub
		
	}

	public void setDirector(Actor next) {
		// TODO Auto-generated method stub
		
	}

	public List<Actor> getListActor() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setListActor(List<Actor> listActor) {
		// TODO Auto-generated method stub
		
	}
}