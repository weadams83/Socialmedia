package com.Assessment.SocialMedia.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.Assessment.SocialMedia.DTOs.HashTagResponseDTO;
import com.Assessment.SocialMedia.DTOs.TweetResponseDTO;
import com.Assessment.SocialMedia.entities.HashTag;
import com.Assessment.SocialMedia.entities.Tweet;
import com.Assessment.SocialMedia.exceptions.BadRequestException;
import com.Assessment.SocialMedia.exceptions.NotFoundException;
import com.Assessment.SocialMedia.mappers.HashTagMapper;
import com.Assessment.SocialMedia.mappers.TweetMapper;
import com.Assessment.SocialMedia.repositories.HashTagRepository;
import com.Assessment.SocialMedia.repositories.TweetRepository;
import com.Assessment.SocialMedia.services.HashTagService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class HashTagImpl implements HashTagService {

	private HashTagRepository hashTagRepository;
	private HashTagMapper hTagMapper;
	private TweetRepository tweetRepo;
	private TweetMapper tweetMap;

	@Override
	public List<HashTagResponseDTO> getAllHashTags() {
		return hTagMapper.entitiesToResponseDTOs(hashTagRepository.findAll());
	}
	
	@Override
	public List<TweetResponseDTO> getAllTweetsWithHashTag(String label) {
		Optional<HashTag> findTag = hashTagRepository.findByLabelIgnoreCase(label);
		if(findTag.isEmpty()) {
			throw new NotFoundException(String.format("No HashTag with the label: '%s' exists.", label));
		}
		List<Tweet> findTweets = tweetRepo.getTweetsTagged(label);
		return tweetMap.entitiesToResponseDTOs(findTweets);
	}
	
	@Override
	public boolean tagExists(String label) {
		Optional<HashTag> hashTag = hashTagRepository.findByLabelIgnoreCase(label);
		return hashTag.isPresent();
	}	

}
