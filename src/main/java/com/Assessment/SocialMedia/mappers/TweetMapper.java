package com.Assessment.SocialMedia.mappers;


import java.util.ArrayList;
import java.util.Collections;

import java.util.List;

import org.mapstruct.Mapper;

import com.Assessment.SocialMedia.entities.TweedleUser;
import com.Assessment.SocialMedia.entities.Tweet;
import com.Assessment.SocialMedia.model.TweedleUserDTO;
import com.Assessment.SocialMedia.model.TweetResponseDTO;

@Mapper(componentModel = "spring")
public interface TweetMapper {


	List<TweetResponseDTO> entitiesToResponseDTOs(List<Tweet> findAll);



}
