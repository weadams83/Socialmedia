package com.Assessment.SocialMedia.DTOs;

import java.sql.Timestamp;

import com.Assessment.SocialMedia.entities.Tweet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TweetRepostDTO {
	private String content;
}
