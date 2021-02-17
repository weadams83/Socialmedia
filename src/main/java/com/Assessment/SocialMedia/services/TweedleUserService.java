package com.Assessment.SocialMedia.services;

import java.util.List;

import com.Assessment.SocialMedia.model.TweedleUserRequestDTO;
import com.Assessment.SocialMedia.model.TweedleUserResponseDTO;

public interface TweedleUserService {
	
	List<TweedleUserResponseDTO> getAllUsers();

	TweedleUserResponseDTO getUser(String userName);

	TweedleUserResponseDTO postUser(TweedleUserRequestDTO tUserRequestDTO);

	TweedleUserResponseDTO patchUser(String userName, TweedleUserRequestDTO tUserRequestDTO);

}
