package com.Assessment.SocialMedia.mappers;


import java.util.ArrayList;
import java.util.Collections;

import java.util.List;

import org.mapstruct.Mapper;

import com.Assessment.SocialMedia.DTOs.TweedleUserDTO;
import com.Assessment.SocialMedia.DTOs.TweetFeedResponseDTO;
import com.Assessment.SocialMedia.DTOs.TweetResponseDTO;
import com.Assessment.SocialMedia.entities.TweedleUser;
import com.Assessment.SocialMedia.entities.Tweet;

@Mapper(componentModel = "spring")
public interface TweetMapper {


	List<TweetResponseDTO> entitiesToResponseDTOs(List<Tweet> findAll);

	List<TweetFeedResponseDTO> entitiesToDTOs(List<Tweet> list);
	



}
