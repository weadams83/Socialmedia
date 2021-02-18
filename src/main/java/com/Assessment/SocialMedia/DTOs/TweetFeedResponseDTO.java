package com.Assessment.SocialMedia.DTOs;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TweetFeedResponseDTO {
	private Timestamp posted;
	private String content;
	private TweetRepostDTO repostOf;
}
