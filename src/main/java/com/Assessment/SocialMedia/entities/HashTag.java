package com.Assessment.SocialMedia.entities;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class HashTag {
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable=false,unique=true)
	private String label;
	
	private final Timestamp firstUsed = new Timestamp(System.currentTimeMillis());
	
	private Timestamp lastUsed;
	
	@ManyToMany(mappedBy="hashtags")
	private List<Tweet> tweets;
}
