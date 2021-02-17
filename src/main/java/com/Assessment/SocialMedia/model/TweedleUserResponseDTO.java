package com.Assessment.SocialMedia.model;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TweedleUserResponseDTO {
	private String userName;
	
	private final Timestamp joined = new Timestamp(System.currentTimeMillis());
}
