package com.Assessment.SocialMedia.services;

import java.util.List;

import com.Assessment.SocialMedia.DTOs.TweedleUserRequestDTO;
import com.Assessment.SocialMedia.DTOs.TweedleUserResponseDTO;
import com.Assessment.SocialMedia.DTOs.TweetFeedResponseDTO;
import com.Assessment.SocialMedia.DTOs.TweetResponseDTO;

public interface TweedleUserService {
	
	List<TweedleUserResponseDTO> getAllUsers();

	TweedleUserResponseDTO getUser(String userName);

	TweedleUserResponseDTO postUser(TweedleUserRequestDTO tUserRequestDTO);

	TweedleUserResponseDTO patchUser(String userName, TweedleUserRequestDTO tUserRequestDTO);

	TweedleUserResponseDTO deleteUser(String userName, TweedleUserRequestDTO tUserRequestDTO);

	void postUserFollow(String username, TweedleUserRequestDTO tUserRequestDTO);

	void postUserUnfollow(String username, TweedleUserRequestDTO tUserRequestDTO);

	List<TweetFeedResponseDTO> getUserFeed(String userName);

	List<TweetFeedResponseDTO> getUserTweets(String userName);

	List<TweetResponseDTO> getUserMentions(String userName);

	List<TweedleUserResponseDTO> getUserFollowers(String userName);

	List<TweedleUserResponseDTO> getUserFollowing(String userName);

	boolean userExists(String username);

}
