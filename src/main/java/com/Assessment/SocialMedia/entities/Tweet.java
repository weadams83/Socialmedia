package com.Assessment.SocialMedia.entities;

import java.sql.Timestamp;
import java.util.List;
import javax.persistence.JoinColumn;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Tweet {
	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
	private TweedleUser author;
	
	private final Timestamp posted = new Timestamp(System.currentTimeMillis());
	
	private String content;
	
	@OneToOne
	private Tweet inReplyTo;
	
	@OneToOne
	private Tweet repostOf;
	
	private boolean deleted;
	
	@ManyToMany//(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "tweetHashJoin",
			joinColumns = @JoinColumn(name = "tweet_id"),
			inverseJoinColumns = @JoinColumn(name = "hashTag_id"))
	private List<HashTag> hashtags;
	
	@Override
	public boolean equals(Object obj) {
    	if(obj == this) {
    		return true;
    	}
    	if(obj == null || obj.getClass() != this.getClass()) {
    		return false;
    	}
    	Tweet tweet = (Tweet) obj;
    	return tweet.getId() == this.getId();
    }
	
	@Override
    public int hashCode() {
    	int hash = 7;
    	for (int i = 0; i < this.getContent().length(); i++) {
    	    hash = hash*31 + this.getContent().charAt(i);
    	}
    	return hash;
    }
}
