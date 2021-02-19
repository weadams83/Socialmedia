package com.Assessment.SocialMedia.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.Assessment.SocialMedia.DTOs.PostTweetDTO;
import com.Assessment.SocialMedia.DTOs.TweedleUserDTO;
import com.Assessment.SocialMedia.DTOs.TweedleUserRequestDTO;
import com.Assessment.SocialMedia.DTOs.TweedleUserResponseDTO;
import com.Assessment.SocialMedia.entities.TweedleUser;

@Mapper(componentModel = "spring",uses= TweetMapper.class)
public interface TweedleUserMapper {
	
	TweedleUser DTOtoEntity(TweedleUserDTO tweedleUserDTO);
	
	List<TweedleUserResponseDTO> entitiesToResponseDTOs(List<TweedleUser> tUsers);
	
	TweedleUserResponseDTO entityToResponseDTO(TweedleUser tweedleUser);
	
	TweedleUser requestDTOtoEntity(TweedleUserRequestDTO tweedleUserRequestDTO);

	TweedleUser postTweetRequesttoEntity(PostTweetDTO postTweetDTO);

}
