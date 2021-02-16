package com.Assessment.SocialMedia.entities;

import javax.persistence.*;

import lombok.Data;


@Embeddable
@Data
public class UserCredentials {

	@Column(nullable = false)
	private String password;
	
}
