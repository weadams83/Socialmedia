package com.Assessment.SocialMedia.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class HashTagRequestDTO {   //The one i expect the to send to create an object
	//user will send this in a request

	private Long id;
	private String label;
	
}
