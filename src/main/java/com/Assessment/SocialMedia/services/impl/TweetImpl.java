package com.Assessment.SocialMedia.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.Assessment.SocialMedia.DTOs.TweedleUserRequestDTO;
import com.Assessment.SocialMedia.DTOs.TweedleUserResponseDTO;
import com.Assessment.SocialMedia.DTOs.TweetResponseDTO;
import com.Assessment.SocialMedia.entities.TweedleUser;
import com.Assessment.SocialMedia.entities.Tweet;
import com.Assessment.SocialMedia.exceptions.BadRequestException;
import com.Assessment.SocialMedia.exceptions.NotFoundException;
import com.Assessment.SocialMedia.mappers.TweetMapper;
import com.Assessment.SocialMedia.repositories.TweedleUserRepository;
import com.Assessment.SocialMedia.repositories.TweetRepository;
import com.Assessment.SocialMedia.services.TweetService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TweetImpl implements TweetService {
	private TweedleUserRepository tUserRepo;
	private TweetRepository tweetRepository;
	private TweetMapper tweetMapper;

	@Override
	public List<TweetResponseDTO> getAllTweets() {

		List<Tweet> result = tweetRepository.getAllTweets();
		return tweetMapper.entitiesToResponseDTOs(result);	
	}

	@Override
	public TweetResponseDTO getTweetByID(Long id) {
		Optional<Tweet> findId = tweetRepository.findById(id);

		if (findId.isEmpty()) {
			throw new NotFoundException("No such tweet exists.");
		}
		if (findId.get().isDeleted()) {
			throw new NotFoundException("This tweet has been deleted.");
		}
		return tweetMapper.entityToResponseDTO(findId.get());
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


	public TweetResponseDTO createTweet(TweetResponseDTO tweetResponseDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TweedleUserResponseDTO> getUsersLikedTweet(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public TweetResponseDTO createTweet(TweetResponseDTO tweetResponseDTO) {
//		//Map requestdto to a tweet entity (not save in database yet, just an java object)
//		TweetResponseDTO tweetToSave =  new TweetResponseDTO(); 
////		tweetToSave.s
//		
//		//Save the new tweet entity and store the resulting entity with the ID generated from the database
//		//tweetrepository accesses the database then- 
//		//the save and flush will return the data with the newly generated id
////		Tweet savedTweet = tweetRepository.saveAndFlush(tweetToSave);
//		
//		//map my newly saved entity with the generated id to a response dto and (What we return to client)
////		TweetResponseDTO result = new TweetResponseDTO(savedTweet.getId(), savedTweet);
////		result.setId(savedTweet.getId());
//		return null; 	
//	}
//
//	@Override
//	public List<TweedleUserResponseDTO> getUsersLikedTweet(Long id) {
//		Optional<Tweet> tweet = tweetRepository.findById(id);
//		//Optional<Tweet> findId = tweetRepository.findBy
//		
//		if (tweet.isEmpty() || tweet.get().isDeleted()) {
//			throw new NotFoundException("No such tweet exists or has been deleted.");
//		}
//		return tweetMapper.entityToResponseDTO(tweet.get());
//	}
//}
//
////	Retrieves the active users who have liked the tweet with the given id.
////	If that tweet is deleted or otherwise doesn't exist, 
////	an error should be sent in lieu of a response.
////
////	IMPORTANT: Deleted users should be excluded from the response.

}
	
	
	
	
	
	
	
	

