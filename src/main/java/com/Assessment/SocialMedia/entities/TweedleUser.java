package com.Assessment.SocialMedia.entities;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@NoArgsConstructor
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")

public class TweedleUser {
	
	@Id
	@GeneratedValue
	private Long id;															//id
	private final Timestamp joined = new Timestamp(System.currentTimeMillis()); //joined date/time
	private boolean deleted;													//deleted
	@Column(nullable = false, unique = true)
	private String userName = "";												//userName

	@OneToMany(mappedBy = "author")
	private List<Tweet> tweets;													//tweets
	
	/* Add embedded classes here */
	@Embedded
	private Profile profile;													//profile
	@Embedded
	private UserCredentials credentials;										//credentials
	/* End Embedded */
	
	
	
	/*
	 * equals() compares the database entries by id and the objects 
	 * in memory in Java
	 */
	@Override
	public boolean equals(Object obj) {
		
		//check null and class
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}
		
		//compare pointers
		if (obj == this) {
			return true;
		}
		
		//compare id's
		return ((TweedleUser) obj).getId() == this.getId();
	}

	@Override
	public int hashCode() {
		/* Simplified hash. Use id field instead of username,
		 * since username can change in the database.
		 */
		return Objects.hash(this.id);
	}
}
