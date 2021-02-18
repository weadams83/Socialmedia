package com.Assessment.SocialMedia.services;

import java.util.List;

import com.Assessment.SocialMedia.model.TweetResponseDTO;

public interface TweetService {

	List<TweetResponseDTO> getAllTweets();

}
