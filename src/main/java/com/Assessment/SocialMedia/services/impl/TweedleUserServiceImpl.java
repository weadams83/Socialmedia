package com.Assessment.SocialMedia.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.Assessment.SocialMedia.entities.TweedleUser;
import com.Assessment.SocialMedia.exceptions.BadRequestException;
import com.Assessment.SocialMedia.exceptions.NotFoundException;
import com.Assessment.SocialMedia.mappers.TweedleUserMapper;
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
}
