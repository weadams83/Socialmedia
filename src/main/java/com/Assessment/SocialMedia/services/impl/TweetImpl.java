package com.Assessment.SocialMedia.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.Assessment.SocialMedia.DTOs.TweetResponseDTO;
import com.Assessment.SocialMedia.entities.Tweet;
import com.Assessment.SocialMedia.mappers.TweetMapper;
import com.Assessment.SocialMedia.repositories.TweetRepository;
import com.Assessment.SocialMedia.services.TweetService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TweetImpl implements TweetService {
	private TweetRepository tweetRepository;
	private TweetMapper tweetMapper;

	@Override
	public List<TweetResponseDTO> getAllTweets() {

		Optional<List<Tweet>> result = tweetRepository.getAllTweets();
		return tweetMapper.entitiesToResponseDTOs(result.get());

	}

}
