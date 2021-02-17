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
			throw new NotFoundException(String.format("User with user name: %s could not be found.", userName));
		}
		if(findUser.get().isDeleted()) {
			throw new NotFoundException(String.format("User with user name: %s has deleted their account.", userName));
		}
		return tUserMap.entityToResponseDTO(findUser.get());
	}

	@Override
	public TweedleUserResponseDTO postUser(TweedleUserRequestDTO tUserRequestDTO) {
		TweedleUser tUser = tUserMap.requestDTOtoEntity(tUserRequestDTO);
		if(tUser.getCredentials().getPassword() == null || tUser.getCredentials().getPassword().length() == 0) {
			throw new BadRequestException("User must have a Password.");
		}
		if(tUser.getCredentials().getUserName() == null || tUser.getCredentials().getUserName().length() == 0) {
			throw new BadRequestException("User must have a Username.");
		}
		if(tUser.getProfile().getEmail() == null || tUser.getProfile().getEmail().length() == 0){
			throw new BadRequestException("User must have an Email.");
		}
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
}
