package com.Assessment.SocialMedia.DTOs;

import java.sql.Timestamp;

import com.Assessment.SocialMedia.entities.Profile;
import com.Assessment.SocialMedia.entities.UserCredentials;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TweedleUserResponseDTO {
//	private String userName;
	private Profile profile; //add embedded classes here
	
	private UserCredentials credentials;
	private final Timestamp joined = new Timestamp(System.currentTimeMillis());
}
