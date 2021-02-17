package com.Assessment.SocialMedia.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class Profile {
	
	@Column
	private String firstName;			//firstName
	
	@Column
	private String lastName;			//lastName
	
	@Column(nullable = false)
	private String email;				//email
	
	@Column
	private String phone;				//phone

}
