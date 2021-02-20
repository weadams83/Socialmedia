package com.Assessment.SocialMedia.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.Assessment.SocialMedia.DTOs.TweedleUserRequestDTO;
import com.Assessment.SocialMedia.DTOs.TweedleUserResponseDTO;
import com.Assessment.SocialMedia.DTOs.TweetFeedResponseDTO;
import com.Assessment.SocialMedia.DTOs.TweetResponseDTO;
import com.Assessment.SocialMedia.entities.TweedleUser;
import com.Assessment.SocialMedia.entities.Tweet;
import com.Assessment.SocialMedia.exceptions.BadRequestException;
import com.Assessment.SocialMedia.exceptions.ImUsedException;
import com.Assessment.SocialMedia.exceptions.NotFoundException;
import com.Assessment.SocialMedia.mappers.TweedleUserMapper;
import com.Assessment.SocialMedia.mappers.TweetMapper;
import com.Assessment.SocialMedia.repositories.TweedleUserRepository;
import com.Assessment.SocialMedia.repositories.TweetRepository;
import com.Assessment.SocialMedia.services.TweedleUserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TweedleUserServiceImpl implements TweedleUserService {
	private TweedleUserRepository tUserRepo;
	private TweetRepository tweetRepo;
	private TweedleUserMapper tUserMap;
	private TweetMapper tweetMap;
	
	private void VetTweedleUserRequestDTO(TweedleUserRequestDTO tUserRequestDTO) {
		if(tUserRequestDTO.getCredentials().getPassword() == null || tUserRequestDTO.getCredentials().getPassword().length() == 0) {
			throw new BadRequestException("User must have a Password.");
		}
		if(tUserRequestDTO.getCredentials().getUserName() == null || tUserRequestDTO.getCredentials().getUserName().length() == 0) {
			throw new BadRequestException("User must have a Username.");
		}
	}

	@Override
	public List<TweedleUserResponseDTO> getAllUsers() {
		List<TweedleUser> tUsers = tUserRepo.queryByDeletedFalse();
		return tUserMap.entitiesToResponseDTOs(tUsers);
	}

	@Override
	public TweedleUserResponseDTO getUser(String userName) {
		Optional<TweedleUser> findUser = tUserRepo.findByCredentialsUserNameIgnoreCase(userName);
		if(findUser.isEmpty()) {
			throw new NotFoundException(String.format("User with username: %s could not be found.", userName));
		}
		if(findUser.get().isDeleted()) {
			throw new NotFoundException(String.format("User with username: %s has deleted their account.", userName));
		}
		return tUserMap.entityToResponseDTO(findUser.get());
	}
	
	@Override
	public List<TweetFeedResponseDTO> getUserFeed(String userName) {
		Optional<TweedleUser> tUser = tUserRepo.findByCredentialsUserNameIgnoreCase(userName);
		if(tUser.isEmpty()) {
			throw new NotFoundException(String.format("User with username: %s could not be found.", userName));
		}
		if(tUser.get().isDeleted()) {
			throw new NotFoundException(String.format("User with username: %s has deleted their account.", userName));
		}
		List<Tweet> tweetFeed = tweetRepo.getTweetFeed(tUser.get().getId());
		List<TweetFeedResponseDTO> tweetFeedDTO = tweetMap.entitiesToDTOs(tweetFeed);
		return tweetFeedDTO;
		
	}

	@Override
	public TweedleUserResponseDTO postUser(TweedleUserRequestDTO tUserRequestDTO) {
		VetTweedleUserRequestDTO(tUserRequestDTO);
		if(tUserRequestDTO.getProfile().getEmail() == null || tUserRequestDTO.getProfile().getEmail().length() == 0){
			throw new BadRequestException("User must have an Email.");
		}
		TweedleUser tUser = tUserMap.requestDTOtoEntity(tUserRequestDTO);
		Optional<TweedleUser> findUser = tUserRepo.findByCredentialsUserNameIgnoreCase(tUser.getCredentials().getUserName());
		//if given credentials match previously deleted user, re-activate and return
		if(findUser.isPresent() && tUser.getCredentials().getPassword().equals(findUser.get().getCredentials().getPassword())){
			findUser.get().setDeleted(false);
			tUserRepo.saveAndFlush(findUser.get());
			return tUserMap.entityToResponseDTO(findUser.get());
		//else if username is taken
		}else if(findUser.isPresent() && !tUser.getCredentials().getPassword().equals(findUser.get().getCredentials().getPassword())){
			throw new ImUsedException("UserName is taken. Please choose a different UserName.");
		}

		tUserRepo.saveAndFlush(tUser);
		return tUserMap.entityToResponseDTO(tUser);
	}

	@Override
	public TweedleUserResponseDTO patchUser(String userName, TweedleUserRequestDTO tUserRequestDTO) {
		VetTweedleUserRequestDTO(tUserRequestDTO);
		TweedleUser tUser = tUserMap.requestDTOtoEntity(tUserRequestDTO);
		Optional<TweedleUser> findUser = tUserRepo.findByCredentialsUserNameIgnoreCase(tUser.getCredentials().getUserName());
		if(findUser.isEmpty()) {
			throw new NotFoundException(String.format("User with username: %s could not be found.", tUser.getCredentials().getUserName()));
		}
		if(findUser.get().isDeleted()) {
			throw new NotFoundException(String.format("User with username: %s has deleted their account.", tUser.getCredentials().getUserName()));
		}
		if(!tUser.getCredentials().getPassword().equals(findUser.get().getCredentials().getPassword())) {
			throw new BadRequestException("Incorrect password.");
		}
		findUser.get().getProfile().setEmail(tUser.getProfile().getEmail());
		String fName = tUser.getProfile().getFirstName();
		if(fName != null && fName.length() > 0) {
			findUser.get().getProfile().setFirstName(fName);
		}
		String lName = tUser.getProfile().getLastName();
		if(lName != null && lName.length() > 0) {
			findUser.get().getProfile().setLastName(lName);
		}
		String phone = tUser.getProfile().getPhone();
		if(phone != null && phone.length() > 0) {
			findUser.get().getProfile().setPhone(phone);
		}
		tUserRepo.saveAndFlush(findUser.get());
		return tUserMap.entityToResponseDTO(findUser.get());
	}

	@Override
	public TweedleUserResponseDTO deleteUser(String userName, TweedleUserRequestDTO tUserRequestDTO) {
		VetTweedleUserRequestDTO(tUserRequestDTO);
		Optional<TweedleUser> findUser = tUserRepo.findByCredentialsUserNameIgnoreCase(userName);
		if(findUser.isEmpty()) {
			throw new NotFoundException(String.format("User with username: %s could not be found.", userName));
		}
		/*
		 * Compare passwords and if true, delete user
		 */
		if(!findUser.get().getCredentials().getPassword().equals(tUserRequestDTO.getCredentials().getPassword())){
			throw new BadRequestException("User credentials do not match.");
		}
		findUser.get().setDeleted(true);
		tUserRepo.saveAndFlush(findUser.get());
		return tUserMap.entityToResponseDTO(findUser.get());
	}
	
	@Override
	public void postUserFollow(String username,TweedleUserRequestDTO tUserRequestDTO) {
		VetTweedleUserRequestDTO(tUserRequestDTO);
		Optional<TweedleUser> findUser = tUserRepo.findByCredentialsUserNameIgnoreCase(tUserRequestDTO.getCredentials().getUserName());
		if(findUser.isEmpty()) {
			throw new BadRequestException(String.format("You can't follow anyone, your username %s doesn't exist.", tUserRequestDTO.getCredentials().getUserName()));
		}
		Optional<TweedleUser> findCelebrity = tUserRepo.findByCredentialsUserNameIgnoreCase(username);
		if(findCelebrity.isEmpty()) {
			throw new BadRequestException(String.format("Can't follow %s, username doesn't exist.", username));
		}
		if(findCelebrity.get().isDeleted()) {
			throw new BadRequestException(String.format("Can't follow %s, username has been deleted.", username));
		}
		if(findCelebrity.get().getFollowedBy().contains(findUser.get())) {
			throw new BadRequestException(String.format("You are already following this person."));
		}
		List<TweedleUser> getFans = findCelebrity.get().getFollowedBy();
		getFans.add(findUser.get());
		findCelebrity.get().setFollowedBy(getFans);
		tUserRepo.saveAndFlush(findCelebrity.get());
	}
	
	@Override
	public void postUserUnfollow(String username, TweedleUserRequestDTO tUserRequestDTO) {
		VetTweedleUserRequestDTO(tUserRequestDTO);
		Optional<TweedleUser> findUser = tUserRepo.findByCredentialsUserNameIgnoreCase(tUserRequestDTO.getCredentials().getUserName());
		if(findUser.isEmpty()) {
			throw new BadRequestException(String.format("You can't unfollow anyone, your username %s doesn't exist.", tUserRequestDTO.getCredentials().getUserName()));
		}
		Optional<TweedleUser> findCelebrity = tUserRepo.findByCredentialsUserNameIgnoreCase(username);
		if(findCelebrity.isEmpty()) {
			throw new BadRequestException(String.format("Can't unfollow %s, username doesn't exist.", username));
		}
		if(findCelebrity.get().isDeleted()) {
			throw new BadRequestException(String.format("Can't unfollow %s, username has been deleted.", username));
		}
		if(!findCelebrity.get().getFollowedBy().contains(findUser.get())) {
			throw new BadRequestException(String.format("You are already unfollowing this person."));
		}
		List<TweedleUser> getFans = findCelebrity.get().getFollowedBy();
		getFans.remove(findUser.get());
		findCelebrity.get().setFollowedBy(getFans);
		tUserRepo.saveAndFlush(findCelebrity.get());
	}

	@Override
	public List<TweetFeedResponseDTO> getUserTweets(String userName) {
		Optional<TweedleUser> findUser = tUserRepo.findByCredentialsUserNameIgnoreCase(userName);
		if(findUser.isEmpty()) {
			throw new NotFoundException(String.format("User with username: %s could not be found.", userName));
		}
		if(findUser.get().isDeleted()) {
			throw new NotFoundException(String.format("User with username: %s has deleted their account.", userName));
		}
		List<Tweet> getTweets = tweetRepo.getMyTweets(findUser.get().getId());
		return tweetMap.entitiesToDTOs(getTweets);
	}
	
	@Override
	public List<TweetResponseDTO> getUserMentions(String userName) {
		Optional<TweedleUser> findUser = tUserRepo.findByCredentialsUserNameIgnoreCase(userName);
		if(findUser.isEmpty()) {
			throw new NotFoundException(String.format("User with username: %s could not be found.", userName));
		}
		if(findUser.get().isDeleted()) {
			throw new NotFoundException(String.format("User with username: %s has deleted their account.", userName));		
		}
		List<Tweet> myMentions = tweetRepo.getMyMentions(findUser.get().getId());
		return tweetMap.entitiesToResponseDTOs(myMentions);
	}

	@Override
	public List<TweedleUserResponseDTO> getUserFollowers(String userName) {
		Optional<TweedleUser> findUser = tUserRepo.findByCredentialsUserNameIgnoreCase(userName);
		if(findUser.isEmpty()) {
			throw new NotFoundException(String.format("User with username: %s could not be found.", userName));
		}
		if(findUser.get().isDeleted()) {
			throw new NotFoundException(String.format("User with username: %s has deleted their account.", userName));		
		}
		List<TweedleUser> myFollowers = tUserRepo.getMyFollowers(findUser.get().getId());
		return tUserMap.entitiesToResponseDTOs(myFollowers);
	}

	@Override
	public List<TweedleUserResponseDTO> getUserFollowing(String userName) {
		Optional<TweedleUser> findUser = tUserRepo.findByCredentialsUserNameIgnoreCase(userName);
		if(findUser.isEmpty()) {
			throw new NotFoundException(String.format("User with username: %s could not be found.", userName));
		}
		if(findUser.get().isDeleted()) {
			throw new NotFoundException(String.format("User with username: %s has deleted their account.", userName));		
		}
		List<TweedleUser> whoIFollow = tUserRepo.getWhoIFollow(findUser.get().getId());
		return tUserMap.entitiesToResponseDTOs(whoIFollow);
	}
	
	@Override
	public boolean userExists(String username) {
		Optional<TweedleUser> user = tUserRepo.findByCredentialsUserNameIgnoreCase(username);
		return user.isPresent();
	}
	
}
