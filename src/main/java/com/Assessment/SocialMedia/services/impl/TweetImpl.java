package com.Assessment.SocialMedia.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.Assessment.SocialMedia.DTOs.TweedleUserRequestDTO;
import com.Assessment.SocialMedia.DTOs.TweetResponseDTO;
import com.Assessment.SocialMedia.entities.TweedleUser;
import com.Assessment.SocialMedia.entities.Tweet;
import com.Assessment.SocialMedia.exceptions.BadRequestException;
import com.Assessment.SocialMedia.exceptions.NotFoundException;

import com.Assessment.SocialMedia.mappers.TweedleUserMapper;
import com.Assessment.SocialMedia.mappers.TweetMapper;
import com.Assessment.SocialMedia.repositories.TweedleUserRepository;
import com.Assessment.SocialMedia.repositories.TweetRepository;
import com.Assessment.SocialMedia.DTOs.TweedleUserRequestDTO;

import com.Assessment.SocialMedia.services.TweetService;


import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TweetImpl implements TweetService {
	private TweedleUserRepository tUserRepo;
	private TweetRepository tweetRepository;
	private TweetMapper tweetMapper;
	private TweedleUserMapper tUserMapper;

	@Override
	public List<TweetResponseDTO> getAllTweets() {

		List<Tweet> result = tweetRepository.getAllTweets();
		return tweetMapper.entitiesToResponseDTOs(result);

	}

	@Override
	public TweetResponseDTO deleteTweet(Long id, TweedleUserRequestDTO tweetUserRequestDTO){
		Optional<Tweet>findTweet = tweetRepository.findById(id);
		if (findTweet.isEmpty()) {
			throw new NotFoundException(String.format("Tweet With id: %d does not exsist", id));
		}
		if (!findTweet.get().getAuthor().getCredentials().getUserName().equals(tweetUserRequestDTO.getCredentials().getUserName())) {
			throw new BadRequestException("Username Incorrect");
		}
		if (!findTweet.get().getAuthor().getCredentials().getPassword().equals(tweetUserRequestDTO.getCredentials().getPassword())) {
			throw new BadRequestException("Password Incorrect");
		}
		findTweet.get().setDeleted(true);
		tweetRepository.saveAndFlush(findTweet.get());
		return tweetMapper.entityToResponseDTO(findTweet.get());
	}
	
	public void postLike(Long id, TweedleUserRequestDTO tweedleUserRequestDTO) {
		Optional<Tweet> findTweet = tweetRepository.findById(id);
		if(findTweet.isEmpty()) {
			throw new NotFoundException(String.format("Tweet with id: %d does not exist.",id));
		}
		if(findTweet.get().isDeleted()) {
			throw new NotFoundException(String.format("Tweet with id: %d has been deleted.",id));
		}
		Optional<TweedleUser> findUser = tUserRepo.findByCredentialsUserNameIgnoreCase(tweedleUserRequestDTO.getCredentials().getUserName());
		
		if(findUser.isEmpty()) {
			throw new NotFoundException("User could not be found.");
		}
		if(!findUser.get().getCredentials().getPassword().equals(tweedleUserRequestDTO.getCredentials().getPassword())) {
			throw new BadRequestException("Password is incorrect.");
		}
		if(findUser.get().isDeleted()) {
			throw new NotFoundException("User has been deleted");
		}
		List<Tweet> likedTweets = findUser.get().getLikedTweets();
		if(!likedTweets.contains(findTweet.get())) {
			likedTweets.add(findTweet.get());
			findUser.get().setLikedTweets(likedTweets);
			tUserRepo.saveAndFlush(findUser.get());
		}
	}

}
