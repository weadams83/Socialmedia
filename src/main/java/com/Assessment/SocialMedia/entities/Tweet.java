package com.Assessment.SocialMedia.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

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
	@Column(nullable=false)
	private User author;
	
	private final Timestamp posted = new Timestamp(System.currentTimeMillis());
	
	private String content;
	
	private Tweet inReplyTo;
	
	private Tweet repostOf;
	
	private boolean deleted;
	
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
