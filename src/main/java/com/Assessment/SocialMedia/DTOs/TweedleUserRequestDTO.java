package com.Assessment.SocialMedia.DTOs;

import com.Assessment.SocialMedia.entities.Profile;
import com.Assessment.SocialMedia.entities.UserCredentials;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TweedleUserRequestDTO {
	
	private Profile profile; 
	
	private UserCredentials credentials; 
	
}
