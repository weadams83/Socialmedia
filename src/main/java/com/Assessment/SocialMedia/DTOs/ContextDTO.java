package com.Assessment.SocialMedia.DTOs;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContextDTO {
	private TweetResponseDTO target;
	private List<TweetResponseDTO> before;
	private List<TweetResponseDTO> after;
}
