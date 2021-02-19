package com.Assessment.SocialMedia.services;

import java.util.List;

import com.Assessment.SocialMedia.DTOs.TweedleUserRequestDTO;

import com.Assessment.SocialMedia.DTOs.TweetResponseDTO;

public interface TweetService {

	List<TweetResponseDTO> getAllTweets();
	
	
	public TweetResponseDTO deleteTweet(Long id, TweedleUserRequestDTO tweetUserRequestDTO);
	
	

}
