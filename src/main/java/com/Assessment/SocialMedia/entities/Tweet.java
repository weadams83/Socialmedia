package com.Assessment.SocialMedia.entities;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

import javax.persistence.JoinColumn;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")

public class Tweet {
	
	@Id
	@GeneratedValue
	private Long id;
	private final Timestamp posted = new Timestamp(System.currentTimeMillis());
	private String content;
	private boolean deleted;
	
	@ManyToOne
	private TweedleUser author;
	
	@ManyToMany
	@JoinTable(name = "mentionedUsers",
		joinColumns = @JoinColumn(name = "mentions"),
		inverseJoinColumns = @JoinColumn(name = "mentioned"))
	private List<TweedleUser> mentions;
	
	/* Message Thread */
	@OneToOne
	private Tweet inReplyTo;
	@OneToOne
	private Tweet repostOf;
	
	/* Hash Tags */
	@ManyToMany
	@JoinTable(name = "tweetHashJoin",
			joinColumns = @JoinColumn(name = "tweet_id"),
			inverseJoinColumns = @JoinColumn(name = "hashTag_id"))
	private List<HashTag> hashtags;
	
	
	
	
	@Override
	public boolean equals(Object obj) {
		
		// check null and class
		if(obj == null || obj.getClass() != this.getClass()) {
			return false;
		}
		// check pointer
    	if(obj == this) {
    		return true;
    	}
    	// check id
    	Tweet tweet = (Tweet) obj;
    	return tweet.getId() == this.getId();
    }
	
	@Override
    public int hashCode() {
    	return Objects.hash(this.getContent());
    }
}
