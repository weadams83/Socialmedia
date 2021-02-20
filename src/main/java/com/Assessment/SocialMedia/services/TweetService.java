package com.Assessment.SocialMedia.services;

import java.util.List;

import com.Assessment.SocialMedia.DTOs.HashTagResponseDTO;
import com.Assessment.SocialMedia.DTOs.PostTweetDTO;
import com.Assessment.SocialMedia.DTOs.TweedleUserRequestDTO;
import com.Assessment.SocialMedia.DTOs.TweedleUserResponseDTO;
import com.Assessment.SocialMedia.DTOs.TweetResponseDTO;

public interface TweetService {

	List<TweetResponseDTO> getAllTweets();

	public TweetResponseDTO deleteTweet(Long id, TweedleUserRequestDTO tweetUserRequestDTO);

	void postLike(Long id, TweedleUserRequestDTO tweedleUserRequestDTO);

	TweetResponseDTO getTweetByID(Long id);

	TweetResponseDTO postTweet(PostTweetDTO postTweetDTO);

	List<TweedleUserResponseDTO> getUsersLikedTweet(Long id);
	
	List<HashTagResponseDTO> getUsersTweetTags(Long id);

	TweetResponseDTO postReply(Long id, PostTweetDTO postTweetDTO);

}
