package com.Assessment.SocialMedia.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.Assessment.SocialMedia.entities.Tweet;
import com.Assessment.SocialMedia.model.TweetFeedResponseDTO;

@Mapper(componentModel = "spring")
public interface TweetMapper {
	TweetFeedResponseDTO entityToDTO(Tweet tweet);
	List<TweetFeedResponseDTO> entitiesToDTOs(List<Tweet> tweets);

}
