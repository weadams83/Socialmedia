package com.Assessment.SocialMedia.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.Assessment.SocialMedia.DTOs.TweetFeedResponseDTO;
import com.Assessment.SocialMedia.DTOs.TweetResponseDTO;
import com.Assessment.SocialMedia.entities.Tweet;

@Mapper(componentModel = "spring")
public interface TweetMapper {

	List<TweetResponseDTO> entitiesToResponseDTOs(List<Tweet> findAll);

	List<TweetFeedResponseDTO> entitiesToDTOs(List<Tweet> list);

	TweetResponseDTO entityToResponseDTO(Tweet tweet);

	Tweet requestDTOtoEntity(Long id);

}
