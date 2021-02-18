package com.Assessment.SocialMedia.services;

import java.util.List;

import com.Assessment.SocialMedia.DTOs.TweetResponseDTO;

public interface TweetService {

	List<TweetResponseDTO> getAllTweets();

}
