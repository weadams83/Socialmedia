package com.Assessment.SocialMedia.entities;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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
public class TweedleUser {
	@Id
	@GeneratedValue
	private Long id;
	
//	@Column(nullable=false, unique=true)
//	private String userName="";
	
	private final Timestamp joined = new Timestamp(System.currentTimeMillis());
		
	@Embedded
	private UserCredentials credentials;
	@Embedded
	private Profile profile;
	
	private boolean deleted;
	
	@OneToMany(mappedBy="author")
	private List<Tweet> tweets;
	
	@Override
	public boolean equals(Object obj) {
    	if(obj == this) {
    		return true;
    	}
    	if(obj == null || obj.getClass() != this.getClass()) {
    		return false;
    	}
    	TweedleUser tweedleUser = (TweedleUser) obj;
    	return tweedleUser.getId() == this.getId();
    }
	
	@Override
    public int hashCode() {
    	int hash = 7;
    	for (int i = 0; i < this.getCredentials().getUserName().length(); i++) {
    	    hash = hash*31 + this.getCredentials().getUserName().charAt(i);
    	}
    	return hash;
    }
}
