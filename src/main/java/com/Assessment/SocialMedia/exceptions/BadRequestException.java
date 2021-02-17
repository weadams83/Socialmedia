package com.Assessment.SocialMedia.exceptions;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class BadRequestException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4623086488416716824L;

	private String message;
}
