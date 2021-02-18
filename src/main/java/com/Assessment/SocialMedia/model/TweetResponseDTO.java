package com.Assessment.SocialMedia.model;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TweetResponseDTO {

	private Long id; 
	private String Content; 
	private Timestamp posted;
	
}
