package com.Assessment.SocialMedia.model;

import java.util.List;

import com.Assessment.SocialMedia.entities.Profile;
import com.Assessment.SocialMedia.entities.UserCredentials;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TweedleUserRequestDTO {
	//private String userName;
	
	private Profile profile; //add embedded classes here
	
	private UserCredentials credentials; 
	
}
