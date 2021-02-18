package com.Assessment.SocialMedia.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.Assessment.SocialMedia.entities.TweedleUser;
import com.Assessment.SocialMedia.exceptions.BadRequestException;
import com.Assessment.SocialMedia.exceptions.ImUsedException;
import com.Assessment.SocialMedia.exceptions.NotFoundException;
import com.Assessment.SocialMedia.mappers.TweedleUserMapper;
import com.Assessment.SocialMedia.model.TweedleUserRequestDTO;
import com.Assessment.SocialMedia.model.TweedleUserResponseDTO;
import com.Assessment.SocialMedia.repositories.TweedleUserRepository;
import com.Assessment.SocialMedia.services.TweedleUserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TweedleUserServiceImpl implements TweedleUserService{
	private TweedleUserRepository tUserRepo;
	private TweedleUserMapper tUserMap;

	@Override
	public List<TweedleUserResponseDTO> getAllUsers() {
		Optional<List<TweedleUser>> tUsers = tUserRepo.queryByDeletedFalse();
		if(tUsers.isEmpty() || tUsers.get().size() == 0) {
			throw new NotFoundException("There are currently no users.");

		}
		return tUserMap.entitiesToResponseDTOs(tUsers.get());
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

		if(!findUser.get().getCredentials().getUserName().equals(tUserRequestDTO.getCredentials().getUserName()) || 
				!findUser.get().getCredentials().getPassword().equals(tUserRequestDTO.getCredentials().getPassword())){
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
	
	private void VetTweedleUserRequestDTO(TweedleUserRequestDTO tUserRequestDTO) {
		if(tUserRequestDTO.getCredentials().getPassword() == null || tUserRequestDTO.getCredentials().getPassword().length() == 0) {
			throw new BadRequestException("User must have a Password.");
		}
		if(tUserRequestDTO.getCredentials().getUserName() == null || tUserRequestDTO.getCredentials().getUserName().length() == 0) {
			throw new BadRequestException("User must have a Username.");
		}
	}

	
}
