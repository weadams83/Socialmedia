package com.Assessment.SocialMedia.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ImUsedException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8921077654181510118L;
	
	private String message;

}
