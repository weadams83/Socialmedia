package com.Assessment.SocialMedia.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.Assessment.SocialMedia.entities.TweedleUser;
import com.Assessment.SocialMedia.model.TweedleUserDTO;
import com.Assessment.SocialMedia.model.TweedleUserRequestDTO;
import com.Assessment.SocialMedia.model.TweedleUserResponseDTO;

@Mapper(componentModel = "spring")
public interface TweedleUserMapper {
	
	TweedleUser DTOtoEntity(TweedleUserDTO tweedleUserDTO);
	
	List<TweedleUserResponseDTO> entitiesToResponseDTOs(List<TweedleUser> tUsers);
	
	TweedleUserResponseDTO entityToResponseDTO(TweedleUser tweedleUser);
	
	TweedleUser requestDTOtoEntity(TweedleUserRequestDTO tweedleUserRequestDTO);
	
}
