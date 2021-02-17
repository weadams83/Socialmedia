package com.Assessment.SocialMedia.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class NotFoundException extends RuntimeException{/**
	 * 
	 */
	private static final long serialVersionUID = 8588958672670821301L;

	private String message;
}
