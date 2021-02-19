package com.Assessment.SocialMedia.DTOs;

import java.util.List;

import com.Assessment.SocialMedia.entities.UserCredentials;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PostTweetDTO {
	private UserCredentials credentials;
	private String content;
}
