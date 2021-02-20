package com.Assessment.SocialMedia.entities;

import javax.persistence.*;

import lombok.Data;


@Embeddable
@Data
public class UserCredentials {
	@Column(nullable=false, unique=true)
	private String userName;
	
	@Column(nullable = false)
	private String password;
	
}
